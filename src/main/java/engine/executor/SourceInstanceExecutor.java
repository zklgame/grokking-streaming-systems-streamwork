package engine.executor;

import api.Source;
import engine.EventQueue;

public class SourceInstanceExecutor extends InstanceExecutor {
    private final Source source;
    private final int instanceId;

    public SourceInstanceExecutor(final Source source, final int instanceId) {
        super(source);
        this.source = source;
        this.instanceId = instanceId;
        source.setupInstance(instanceId);
    }

    @Override
    public boolean runOnce() {
        try {
            source.getEvents(eventCollector);
            emitEvents();
        } catch (final Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void setIncomingQueue(final EventQueue queue) {
        throw new RuntimeException("SourceInstanceExecutor cannot setIncomingQueue!");
    }
}

