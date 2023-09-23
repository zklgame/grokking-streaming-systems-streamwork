package job;

import api.Event;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VehicleEvent extends Event {
    private final String type;

    @Override
    public String getData() {
        return type;
    }
}
