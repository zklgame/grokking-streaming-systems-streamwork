package api.window.strategy;

import api.Event;
import api.window.EventWindow;
import api.window.TimedEvent;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SlidingWindowingStrategy implements WindowStrategy {
    // Window length
    private final long lengthMills;
    // Interval between two adjacent windows.
    // For fixed windows, interval should be equivalent to length.
    // For sliding windows, interval is shorter than length.
    // The start time of each window should be interval x N.
    private final long intervalMills;
    // Extra wait time before window closing.
    // A window should be closed when the current processing time is greater than the window end time (event time based) + an extra watermark time.
    private final long watermarkMills;

    // startTime: event window
    private final Map<Long, EventWindow> eventWindowMap = new HashMap<>();

    @Override
    public void add(final Event event, final long processingTime) {
        if (!(event instanceof TimedEvent)) {
            throw new RuntimeException("Only TimedEvent can be applied to WindowingStrategy.");
        }

        final TimedEvent timedEvent = (TimedEvent) event;
        final long eventTime = timedEvent.getTime();

        // ignore late event
        if (isLateEvent(eventTime, processingTime)) {
            return;
        }

        long startTime = eventTime / intervalMills * intervalMills;
        while (eventTime < startTime + lengthMills) {
            final EventWindow eventWindow = eventWindowMap.getOrDefault(startTime, new EventWindow(startTime, startTime + lengthMills));
            eventWindow.add(timedEvent);
            eventWindowMap.put(startTime, eventWindow);

            // move to the previous window
            startTime -= lengthMills;
        }
    }

    @Override
    public List<EventWindow> getEventWindows(final long processingTime) {
        final ArrayList<EventWindow> eventWindowsToProcess = new ArrayList<>();

        for (final Map.Entry<Long, EventWindow> entry : eventWindowMap.entrySet()) {
            if (processingTime >= entry.getKey() + lengthMills + watermarkMills) {
                eventWindowsToProcess.add(entry.getValue());
            }
        }

        for (final EventWindow window : eventWindowsToProcess) {
            eventWindowMap.remove(window.getStartTime());
        }

        return eventWindowsToProcess;
    }

    private boolean isLateEvent(final long eventTime, final long processingTime) {
        // e.g., 0:59 + 1 min + 3s < 1:05
        // e.g., 1:03 + 1 min + 3s > 1:05
        return eventTime + lengthMills + watermarkMills < processingTime;
    }
}
