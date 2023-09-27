package engine.executor;

import api.Component;
import api.Event;
import api.EventCollector;
import engine.EventQueue;
import engine.Process;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Getter
@Setter
public abstract class InstanceExecutor extends Process {
    private final Component component;
    protected final EventCollector eventCollector = new EventCollector();

    protected EventQueue incomingQueue = null;
    protected final Map<String, List<EventQueue>> outgoingQueues = new HashMap<>();

    public void registerChannel(final String channel) {
        eventCollector.registerChannel(channel);
    }

    public void addOutgoingQueue(final String channel, final EventQueue eventQueue) {
        final List<EventQueue> eventQueues = outgoingQueues.getOrDefault(channel, new ArrayList<>());
        eventQueues.add(eventQueue);
        outgoingQueues.put(channel, eventQueues);
    }

    protected void emitEvents() throws InterruptedException {
        for (final String channel : eventCollector.getRegisteredChannels()) {
            for (final Event event : eventCollector.getChannelEvents().get(channel)) {
                for (final EventQueue eventQueue : outgoingQueues.get(channel)) {
                    eventQueue.put(event);
                }
            }
        }

        eventCollector.clear();
    }
}
