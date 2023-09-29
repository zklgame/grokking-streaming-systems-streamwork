package api;

import java.util.UUID;

public abstract class Event {
    private final String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }

    public Object getData() {
        return this;
    }
}
