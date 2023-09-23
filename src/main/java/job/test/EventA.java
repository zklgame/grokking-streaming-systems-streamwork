package job.test;

import api.Event;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class EventA extends Event {
    private final String vehicle;
    private final int count;

    @Override
    public Map<String, Integer> getData() {
        return Map.of(vehicle, count);
    }
}
