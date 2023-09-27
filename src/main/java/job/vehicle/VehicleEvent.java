package job.vehicle;

import api.Event;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VehicleEvent extends Event {
    private final String type;
    public String getData() {
        return type;
    }
}
