package api;

import static api.Stream.DEFAULT_CHANNEL;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class EventCollector {
    private final Set<String> registeredChannels = new HashSet<>();
    private final Map<String, List<Event>> channelEvents = new HashMap<>();

    public void registerChannel(final String channel) {
        registeredChannels.add(channel);
        if (!channelEvents.containsKey(channel)) {
            channelEvents.put(channel, new ArrayList<>());
        }
    }

    public void add(final Event event) {
        add(DEFAULT_CHANNEL, event);
    }

    public void add(final String channel, final Event event) {
        if (registeredChannels.contains(channel)) {
            channelEvents.get(channel).add(event);
        }
    }

    public void clear() {
        for (final List<Event> events : channelEvents.values()) {
            events.clear();
        }
    }
}
