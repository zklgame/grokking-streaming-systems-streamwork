package engine;

import api.Job;
import api.Operator;
import api.Source;

import java.util.ArrayList;
import java.util.List;

public class JobStarter {
    private final Job job;

    private final List<ComponentExecutor> executorList = new ArrayList<>();
    private final List<Connection> connectionList = new ArrayList<>();

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

    private void connectByQueue() {
        for (final Connection connection : connectionList) {
            final EventQueue eventQueue = new EventQueue(QUEUE_SIZE);
            connection.getFrom().setOutgoingQueue(eventQueue);
            connection.getTo().setIncomingQueue(eventQueue);
        }
    }
}
