package engine;

import api.Job;
import api.Operator;
import api.Source;
import engine.executor.ComponentExecutor;
import engine.executor.OperatorExecutor;
import engine.executor.SourceExecutor;

import java.util.ArrayList;
import java.util.List;

public class JobStarter {
    private final Job job;

    private final List<ComponentExecutor> executorList = new ArrayList<>();
    private final List<Connection> connectionList = new ArrayList<>();
    private final List<EventDispatcher> eventDispatcherList = new ArrayList<>();

    private static final int QUEUE_SIZE = 64;

    /*
        Example: Source -> O1 -> O2
                 Source -> O3 -> O4
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
        for (final Operator operator : executor.getComponent().getOutgoingStream().getOperatorSet()) {
            final OperatorExecutor operatorExecutor = new OperatorExecutor(operator);
            connectionList.add(new Connection(executor, operatorExecutor));
            traverseExecutor(operatorExecutor);
        }
    }

    // Each component executor could connect to multiple downstream operator executors.
    private void connectByQueue() {
        for (final Connection connection : connectionList) {
            final EventDispatcher eventDispatcher = new EventDispatcher(connection.getTo());
            eventDispatcherList.add(eventDispatcher);

            final EventQueue upstream = new EventQueue(QUEUE_SIZE);
            connection.getFrom().setOutgoingQueue(upstream);
            eventDispatcher.setIncomingQueue(upstream);

            final int parallelism = connection.getTo().getComponent().getParallelism();
            final EventQueue[] downstream = new EventQueue[parallelism];
            for (int i = 0; i < parallelism; i ++) {
                downstream[i] = new EventQueue(QUEUE_SIZE);
            }

            eventDispatcher.setOutgoingQueues(downstream);
            connection.getTo().setIncomingQueues(downstream);
        }
    }
}
