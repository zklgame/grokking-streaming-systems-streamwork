package api.window;

import api.Event;

public abstract class TimedEvent extends Event {
    public abstract long getTime();
}
