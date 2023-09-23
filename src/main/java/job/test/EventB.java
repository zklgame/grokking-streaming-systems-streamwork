package job.test;

import api.Event;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventB extends Event {
    private final boolean isCar;
    @Override
    public Boolean getData() {
        return isCar;
    }
}
