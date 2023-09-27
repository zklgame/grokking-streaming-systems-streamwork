package engine;

import api.Job;
import api.Operator;
import api.Source;
import engine.executor.ComponentExecutor;
import engine.executor.OperatorExecutor;
import engine.executor.SourceExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JobStarter {
    private final Job job;

    private final List<ComponentExecutor> executorList = new ArrayList<>();
    private final List<Connection> connectionList = new ArrayList<>();
    private final List<EventDispatcher> eventDispatcherList = new ArrayList<>();
    // for the case O1 -> O3 and O2 -> O3,
    // we only need to create O3 once.
    private final Map<Operator, OperatorExecutor> operatorToExecutorMap = new HashMap<>();
    // from -> (upstream) -> eventDispatcher -> (downstream) -> to
    // for the toExecutor in connection, if it exists before, reuse the upstream.
    private final Map<OperatorExecutor, EventQueue> toToUpstream = new HashMap<>();

    private static final int QUEUE_SIZE = 64;

    /*
        Example: Source -> C1: O1 -> O2
                 Source -> C2: O3 -> O4
        executorList:   Source -> O1 -> O2 -> O3 -> O4
        connectionList: (Source, O1) (O1, O2) (Source, O3) (O3, O4)
     */
    public JobStarter(final Job job) {
        this.job = job;

        init();
        connectByQueue();
    }

    public void start() {
        System.out.println("Job start: " + job.getName());

        for (int i = executorList.size() - 1; i >= 0; i --) {
            executorList.get(i).start();
        }

        for (final EventDispatcher eventDispatcher : eventDispatcherList) {
            eventDispatcher.start();
        }

        new WebServer().start(connectionList);
    }

    private void init() {
        for (final Source source : job.getSourceSet()) {
            traverseExecutor(new SourceExecutor(source));
        }
    }

    private void traverseExecutor(final ComponentExecutor executor) {
        executorList.add(executor);

        final Map<String, Set<Operator>> operatorMap = executor.getComponent().getOutgoingStream().getOperatorMap();
        for (final String channel : operatorMap.keySet()) {
            for (final Operator operator : operatorMap.get(channel)) {
                final OperatorExecutor operatorExecutor;
                if (operatorToExecutorMap.containsKey(operator)) {
                    operatorExecutor = operatorToExecutorMap.get(operator);
                } else {
                    operatorExecutor = new OperatorExecutor(operator);
                    operatorToExecutorMap.put(operator, operatorExecutor);
                    traverseExecutor(operatorExecutor);
                }

                connectionList.add(new Connection(executor, operatorExecutor, channel));
            }
        }
    }

    // Each component executor could connect to multiple downstream operator executors.
    // from -> (upstream) -> eventDispatcher -> (downstream) -> to
    private void connectByQueue() {
        for (final Connection connection : connectionList) {
            connection.getFrom().registerChannel(connection.getChannel());

            final EventQueue upstream;
            if (!toToUpstream.containsKey(connection.getTo())) {
                upstream = new EventQueue(QUEUE_SIZE);

                final EventDispatcher eventDispatcher = new EventDispatcher(connection.getTo());
                eventDispatcherList.add(eventDispatcher);
                eventDispatcher.setIncomingQueue(upstream);

                final int parallelism = connection.getTo().getComponent().getParallelism();
                final EventQueue[] downstream = new EventQueue[parallelism];
                for (int i = 0; i < parallelism; i ++) {
                    downstream[i] = new EventQueue(QUEUE_SIZE);
                }

                eventDispatcher.setOutgoingQueues(downstream);
                connection.getTo().setIncomingQueues(downstream);

                toToUpstream.put(connection.getTo(), upstream);
            } else {
                upstream = toToUpstream.get(connection.getTo());
            }

            connection.getFrom().addOutgoingQueue(connection.getChannel(), upstream);
        }
    }
}
