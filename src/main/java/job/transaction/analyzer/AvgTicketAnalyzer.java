package job.transaction.analyzer;

import api.Event;
import api.EventCollector;
import api.Operator;
import api.groupingStrategy.GroupingStrategy;
import job.transaction.event.TransactionEvent;
import job.transaction.event.TransactionScoreEvent;

public class AvgTicketAnalyzer extends Operator {
    private int instanceId;

    public AvgTicketAnalyzer(final String name, final int parallelism, final GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void apply(final Event event, final EventCollector eventCollector) {
        System.out.println("\nAvgTicketAnalyzer :: instance " + instanceId + " --> " + ((TransactionEvent) event).getTransactionId());
        eventCollector.add(new TransactionScoreEvent((TransactionEvent) event, 1));
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }
}
