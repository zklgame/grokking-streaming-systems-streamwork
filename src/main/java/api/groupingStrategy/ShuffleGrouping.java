package api.groupingStrategy;

import api.Event;

import java.io.Serializable;

public class ShuffleGrouping implements GroupingStrategy, Serializable {
    private int idx = 0;

    // use round-robin here.
    @Override
    public int getInstanceId(final Event event, final int parallelism) {
        if (idx >= parallelism) {
            idx %= parallelism;
        }
        idx += 1;
        return idx - 1;
    }
}
