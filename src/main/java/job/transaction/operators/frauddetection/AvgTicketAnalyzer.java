package job.transaction.operators.frauddetection;

import api.Event;
import api.EventCollector;
import api.Operator;
import api.deliveryStrategy.DeliveryStrategy;
import api.groupingStrategy.GroupingStrategy;
import job.transaction.event.TransactionEvent;
import job.transaction.event.TransactionScoreEvent;

import java.util.Random;

public class AvgTicketAnalyzer extends Operator {
    private int instanceId;

    public AvgTicketAnalyzer(final String name, final int parallelism, final GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    public AvgTicketAnalyzer(final String name, final int parallelism, final GroupingStrategy groupingStrategy, final DeliveryStrategy deliveryStrategy) {
        super(name, parallelism, groupingStrategy, deliveryStrategy);
    }

    @Override
    public void apply(final Event event, final EventCollector eventCollector) {
        System.out.println("\nAvgTicketAnalyzer :: instance " + instanceId + " --> " + ((TransactionEvent) event).getTransactionId());

        if (((TransactionEvent) event).getAmount() <= 0) {
            final String error = "Transaction amount must be larger than 0! Received: " + ((TransactionEvent) event).getAmount();
            System.out.println(error);

            throw new RuntimeException(error);
        }

        eventCollector.add(new TransactionScoreEvent((TransactionEvent) event, 1));
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }
}
