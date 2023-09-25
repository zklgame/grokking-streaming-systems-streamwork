package engine.executor;

import api.Component;
import engine.EventQueue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ComponentExecutor {
    private final Component component;
    protected final InstanceExecutor[] instanceExecutors;

    public ComponentExecutor(final Component component) {
        this.component = component;
        this.instanceExecutors = new InstanceExecutor[component.getParallelism()];
    }

    public void start() {
        for (final InstanceExecutor instanceExecutor : instanceExecutors) {
            instanceExecutor.start();
        }
    }

    public void setIncomingQueues(final EventQueue[] eventQueues) {
        for (int i = 0; i < eventQueues.length; i ++) {
            instanceExecutors[i].setIncomingQueue(eventQueues[i]);
        }
    }

    public void setOutgoingQueue(final EventQueue eventQueue) {
        for (final InstanceExecutor instanceExecutor : instanceExecutors) {
            instanceExecutor.setOutgoingQueue(eventQueue);
        }
    }
}
