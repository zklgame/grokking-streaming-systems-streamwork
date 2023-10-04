package api.window;

import api.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
public class EventWindow {
    private final long startTime;
    private final long endTime;
    private final List<Event> events = new ArrayList<>();

    public void add(final Event event) {
        events.add(event);
    }
}
