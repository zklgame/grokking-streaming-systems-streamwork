package api.groupingStrategy;

import api.Event;

import java.io.Serializable;

public class FieldsGrouping implements GroupingStrategy, Serializable {

    /**
     * Get key from an event. Child class can override this function
     * to calculate key in different ways. For example, calculate the
     * key from some specific fields.
     * @param event The event object to extract key from.
     * @return The data to be hashed.
     */
    protected Object getKey(final Event event) {
        return event.getData();
    }

    @Override
    public int getInstanceId(final Event event, final int parallelism) {
        return Math.abs(getKey(event).hashCode()) % parallelism;
    }
}
