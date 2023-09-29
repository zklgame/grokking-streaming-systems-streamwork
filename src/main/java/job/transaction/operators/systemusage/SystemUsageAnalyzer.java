package job.transaction.operators.systemusage;

import api.Event;
import api.EventCollector;
import api.Operator;
import api.deliveryStrategy.DeliveryStrategy;
import api.groupingStrategy.GroupingStrategy;
import job.transaction.event.TransactionEvent;
import job.transaction.event.SystemUsageEvent;

import java.util.Random;

public class SystemUsageAnalyzer extends Operator {
    private int instanceId;
    private int transactionCount = 0;
    private int fraudTransactionCount = 0;

    public SystemUsageAnalyzer(final String name, final int parallelism, final GroupingStrategy groupingStrategy, final DeliveryStrategy deliveryStrategy) {
        super(name, parallelism, groupingStrategy, deliveryStrategy);
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public void apply(final Event event, final EventCollector eventCollector) {
        System.out.println("\nSystemUsageAnalyzer :: instance " + instanceId + " --> " + ((TransactionEvent) event).getTransactionId());

        if (((TransactionEvent) event).getAmount() <= 0 && new Random().nextInt() % 2 == 0) {
            final String error = "Transaction amount must be larger than 0! Received: " + ((TransactionEvent) event).getAmount();
            System.out.println(error);

            throw new RuntimeException(error);
        }

        transactionCount += 1;
        eventCollector.add(new SystemUsageEvent(transactionCount, fraudTransactionCount));
    }
}
