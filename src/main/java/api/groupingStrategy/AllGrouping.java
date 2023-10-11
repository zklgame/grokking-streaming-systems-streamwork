package api.groupingStrategy;

import api.Event;

import java.io.Serializable;

public class AllGrouping implements GroupingStrategy, Serializable {
    @Override
    public int getInstanceId(final Event event, final int parallelism) {
        return ALL_INSTANCES;
    }
}
