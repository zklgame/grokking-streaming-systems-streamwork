package engine;

import api.Event;
import engine.executor.OperatorExecutor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class EventDispatcher extends Process {
    private final OperatorExecutor downstreamExecutor;
    private EventQueue incomingQueue = null;
    private EventQueue[] outgoingQueues = null;

    @Override
    public boolean runOnce() {
        try {
            final Event event = incomingQueue.take();
            final int instanceId = downstreamExecutor.getGroupingStrategy().getInstanceId(event, outgoingQueues.length);
            outgoingQueues[instanceId].put(event);
        } catch (final Exception e) {
            return false;
        }
        return true;
    }
}
