package engine;

import api.Event;

import java.util.concurrent.ArrayBlockingQueue;

public class EventQueue extends ArrayBlockingQueue<Event> {
    public EventQueue(final int size) {
        super(size);
    }
}
