package engine;

import api.Event;
import static api.groupingStrategy.GroupingStrategy.ALL_INSTANCES;
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
            final int instanceId = downstreamExecutor.getGroupingStrategy(event.getStreamName()).getInstanceId(event, outgoingQueues.length);
            if (instanceId == ALL_INSTANCES) {
                for (final EventQueue outgoingQueue : outgoingQueues) {
                    outgoingQueue.put(event);
                }
            } else {
                outgoingQueues[instanceId].put(event);
            }
        } catch (final Exception e) {
            return false;
        }
        return true;
    }
}
