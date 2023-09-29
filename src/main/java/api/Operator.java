package api;

import api.deliveryStrategy.DeliveryStrategy;
import api.groupingStrategy.GroupingStrategy;
import api.groupingStrategy.ShuffleGrouping;
import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class Operator extends Component implements Serializable {
    private final GroupingStrategy groupingStrategy;
    private final DeliveryStrategy deliveryStrategy;

    public Operator(final String name, final int parallelism) {
        super(name, parallelism);
        this.groupingStrategy = new ShuffleGrouping();
        this.deliveryStrategy = DeliveryStrategy.AT_MOST_ONCE;
    }

    public Operator(final String name, final int parallelism, final GroupingStrategy groupingStrategy) {
        super(name, parallelism);
        this.groupingStrategy = groupingStrategy;
        this.deliveryStrategy = DeliveryStrategy.AT_MOST_ONCE;
    }

    public Operator(final String name, final int parallelism, final GroupingStrategy groupingStrategy, final DeliveryStrategy deliveryStrategy) {
        super(name, parallelism);
        this.groupingStrategy = groupingStrategy;
        this.deliveryStrategy = deliveryStrategy;
    }

    /**
     * Apply logic to the incoming event and generate results.
     * The function is abstract and needs to be implemented by users.
     * @param event The incoming event
     * @param eventCollector The outgoing event collector
     */
    public abstract void apply(final Event event, final EventCollector eventCollector);
}
