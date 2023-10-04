package api.window;

import api.Event;
import api.EventCollector;
import api.Operator;
import api.groupingStrategy.GroupingStrategy;

public abstract class WindowedOperator extends Operator {
    public WindowedOperator(final String name, final int parallelism, final GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void apply(final Event event, final EventCollector eventCollector) {
        throw new RuntimeException("apply(Event, EventCollector) is not supported in WindowOperator.");
    }

    public abstract void apply(final EventWindow window, final EventCollector eventCollector);
}
