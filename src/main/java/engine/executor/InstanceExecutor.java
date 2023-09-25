package engine.executor;

import api.Component;
import api.Event;
import engine.EventQueue;
import engine.Process;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class InstanceExecutor extends Process {
    private final Component component;
    protected final List<Event> eventCollector = new ArrayList<>();

    protected EventQueue incomingQueue = null;
    protected EventQueue outgoingQueue = null;

    protected void emitEvents() throws InterruptedException {
        for (final Event event : eventCollector) {
            outgoingQueue.put(event);
        }
        eventCollector.clear();
    }
}
