package api.groupingStrategy;

import api.Event;

public interface GroupingStrategy {
    public static final int ALL_INSTANCES = -1;

    /**
     * Get target instance id from an event and component parallelism.
     * Note that in this implementation, only one instance is selected.
     * This can be easily extended if needed.
     * @param event The event object to route to the component.
     * @param The parallelism of the component.
     * @return The instance id.
     */
    int getInstanceId(final Event event, final int parallelism);
}
