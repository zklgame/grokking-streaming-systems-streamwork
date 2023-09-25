package engine.executor;

import api.Source;
import engine.EventQueue;
import org.apache.commons.lang3.SerializationUtils;

public class SourceExecutor extends ComponentExecutor {
    private final Source source;

    public SourceExecutor(final Source source) {
        super(source);
        this.source = source;

        for (int i = 0; i < source.getParallelism(); i ++) {
            final Source cloned = SerializationUtils.clone(source);
            instanceExecutors[i] = new SourceInstanceExecutor(cloned, i);
        }
    }


    @Override
    public void setIncomingQueues(final EventQueue[] eventQueues) {
        throw new RuntimeException("SourceExecutor cannot setIncomingQueues!");
    }
}
