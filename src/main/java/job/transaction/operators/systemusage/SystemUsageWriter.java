package job.transaction.operators.systemusage;

import api.Event;
import api.EventCollector;
import api.Operator;
import api.deliveryStrategy.DeliveryStrategy;
import api.groupingStrategy.GroupingStrategy;

public class SystemUsageWriter extends Operator {
    private int instanceId;

    public SystemUsageWriter(final String name, final int parallelism, final GroupingStrategy groupingStrategy, final DeliveryStrategy deliveryStrategy) {
        super(name, parallelism, groupingStrategy, deliveryStrategy);
    }

    @Override
    public void setupInstance(int instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public void apply(final Event event, final EventCollector eventCollector) {
        System.out.println("\nSystemUsageWriter :: instance " + instanceId + " --> " + event);
    }
}
