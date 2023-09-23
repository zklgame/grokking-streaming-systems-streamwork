package engine;

import api.Source;

public class SourceExecutor extends ComponentExecutor {
    private final Source source;

    public SourceExecutor(final Source source) {
        super(source);
        this.source = source;
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
        throw new RuntimeException("SourceExecutor cannot setIncomingQueue!");
    }
}
