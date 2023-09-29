package engine.executor;

import api.Event;
import api.Operator;
import api.deliveryStrategy.DeliveryStrategy;


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

            switch (operator.getDeliveryStrategy()) {
                case AT_LEAST_ONCE: {
                    acknowledger.addToCache(incomingQueue, event);

                    try {
                        operator.apply(event, eventCollector);
                        acknowledger.ack(incomingQueue, event.getId());
                    } catch (final Exception e) {
                        acknowledger.fail(incomingQueue, event.getId());
                    }

                    break;
                }
                default:
                    operator.apply(event, eventCollector);
            }

            emitEvents();
        } catch (final Exception e) {
            return false;
        }

        return true;
    }
}
