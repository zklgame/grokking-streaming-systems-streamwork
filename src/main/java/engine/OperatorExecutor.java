package engine;

import api.Event;
import api.Operator;


public class OperatorExecutor extends ComponentExecutor {
    private final Operator operator;

    public OperatorExecutor(final Operator operator) {
        super(operator);
        this.operator = operator;
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
