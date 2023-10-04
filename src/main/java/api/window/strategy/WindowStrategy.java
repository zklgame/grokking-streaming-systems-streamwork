package api.window.strategy;

import api.Event;
import api.window.EventWindow;

import java.io.Serializable;
import java.util.List;

public interface WindowStrategy extends Serializable {
    /**
     * Add an event into the windowing strategy.
     * Note that all calculation in this function are event time based, except the logic to check whether the event is a late event or not.
     *
     * @param event
     * @param processingTime
     */
    void add(final Event event, final long processingTime);

    /**
     * Get the event windows that are ready to be processed.
     * It is based on the processing time.
     *
     * @param processingTime
     * @return
     */
    List<EventWindow> getEventWindows(final long processingTime);
}
