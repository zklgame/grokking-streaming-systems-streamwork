package api;

import api.groupingStrategy.GroupingStrategy;
import api.groupingStrategy.ShuffleGrouping;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public abstract class Operator extends Component implements Serializable {
    private final GroupingStrategy groupingStrategy;

    public Operator(final String name, final int parallelism) {
        super(name, parallelism);
        this.groupingStrategy = new ShuffleGrouping();
    }

    public Operator(final String name, final int parallelism, final GroupingStrategy groupingStrategy) {
        super(name, parallelism);
        this.groupingStrategy = groupingStrategy;
    }

    /**
     * Apply logic to the incoming event and generate results.
     * The function is abstract and needs to be implemented by users.
     * @param event The incoming event
     * @param eventCollector The outgoing event collector
     */
    public abstract void apply(final Event event, final List<Event> eventCollector);
}
