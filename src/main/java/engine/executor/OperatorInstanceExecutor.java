package engine.executor;

import api.Event;
import api.Operator;


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
            final Event event = incomingQueue.take();
            operator.apply(event, eventCollector);

            emitEvents();
        } catch (final Exception e) {
            return false;
        }

        return true;
    }
}
