package engine;

import api.Event;
import api.EventCache;

import java.util.HashMap;
import java.util.Map;

public class Acknowledger {
    private final Map<EventQueue, Map<String, EventCache>> queueEventCache = new HashMap<>();

    private static final int MAX_RESEND_COUNT = 3;

    public void addToCache(final EventQueue eventQueue, final Event event) {
        if (!queueEventCache.containsKey(eventQueue)) {
            queueEventCache.put(eventQueue, new HashMap<>());
        }

        if (queueEventCache.get(eventQueue).containsKey(event.getId())) {
            queueEventCache.get(eventQueue).get(event.getId()).addResendCount();
        } else {
            queueEventCache.get(eventQueue).put(event.getId(), new EventCache(event));
        }
    }

    public void ack(final EventQueue eventQueue, final String eventId) {
        removeCache(eventQueue, eventId);
    }

    public void fail(final EventQueue eventQueue, final String eventId) {
        final EventCache eventCache = queueEventCache.getOrDefault(eventQueue, new HashMap<>()).getOrDefault(eventId, null);

        if (eventCache == null) {
            return;
        }

        if (eventCache.getResendCount() >= MAX_RESEND_COUNT) {
            removeCache(eventQueue, eventId);
            return;
        }

        try {
            eventQueue.put(eventCache.getEvent());
        } catch (final InterruptedException e) {
            System.out.println("Failed to put the event back to the queue: " + eventCache.getEvent());
        }
    }

    private EventCache removeCache(final EventQueue eventQueue, final String eventId) {
        return queueEventCache.getOrDefault(eventQueue, new HashMap<>()).remove(eventId);
    }
}
