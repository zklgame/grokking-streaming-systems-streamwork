package api.window;

import api.Event;
import api.EventCollector;
import api.Operator;
import api.window.strategy.WindowStrategy;

import java.util.List;

public final class InternalWindowedOperator extends Operator {
    private final WindowedOperator operator;
    private final WindowStrategy strategy;

    public InternalWindowedOperator(final WindowedOperator operator, final WindowStrategy strategy) {
        super(operator.getName(), operator.getParallelism(), operator.getGroupingStrategy());
        this.operator = operator;
        this.strategy = strategy;
    }

    @Override
    public void setupInstance(final int instanceId) {
        operator.setupInstance(instanceId);
    }

    @Override
    public void apply(final Event event, final EventCollector eventCollector) {
        final long processingTime = System.currentTimeMillis();
        if (event != null) {
            strategy.add(event, processingTime);
        }

        final List<EventWindow> windows = strategy.getEventWindows(processingTime);
        for (final EventWindow window : windows) {
            operator.apply(window, eventCollector);
        }
    }
}
