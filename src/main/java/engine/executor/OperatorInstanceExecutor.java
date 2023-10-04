package engine.executor;

import api.Event;
import api.Operator;
import api.window.InternalWindowedOperator;

import java.util.concurrent.TimeUnit;


public class OperatorInstanceExecutor extends InstanceExecutor {
    private final Operator operator;
    private final int instanceId;

    public OperatorInstanceExecutor(final Operator operator, final int instanceId) {
        super(operator);
        this.operator = operator;
        this.instanceId = instanceId;
        operator.setupInstance(instanceId);
    }

    @Override
    public boolean runOnce() {
        try {
            // Read input. Time out every one second to check if there is any event windows ready to be processed.
            final Event event = incomingQueue.poll(1, TimeUnit.SECONDS);

            if (operator instanceof InternalWindowedOperator || event != null) {
                // window operator handles null event too
                operator.apply(event, eventCollector);
            }

            emitEvents();
        } catch (final Exception e) {
            return false;
        }

        return true;
    }
}
