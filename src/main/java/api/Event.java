package api;

public abstract class Event {
    public String getStreamName() {
        return "default";
    }

    public abstract Object getData();
}
