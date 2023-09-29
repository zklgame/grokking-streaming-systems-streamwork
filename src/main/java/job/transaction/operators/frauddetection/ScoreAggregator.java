package job.transaction.operators.frauddetection;

import api.Event;
import api.EventCollector;
import api.Operator;
import api.groupingStrategy.GroupingStrategy;
import job.transaction.ScoreStorage;
import job.transaction.event.TransactionScoreEvent;

public class ScoreAggregator extends Operator {
    private int instanceId;
    private final ScoreStorage scoreStorage = new ScoreStorage();

    public ScoreAggregator(final String name, final int parallelism, final GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void apply(final Event event, final EventCollector eventCollector) {
        final TransactionScoreEvent transactionScoreEvent = (TransactionScoreEvent) event;
        System.out.println("\nScoreAggregator :: instance " + instanceId + " --> " + transactionScoreEvent.getTransactionEvent().getTransactionId());
        scoreStorage.add(transactionScoreEvent.getTransactionEvent().getTransactionId(), transactionScoreEvent.getScore());
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }
}
