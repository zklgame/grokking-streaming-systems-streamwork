package job.join.event;

import api.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class VehicleEvent extends Event {
    private final String make;
    private final String model;
    private final int year;
    private final int zoneId;
    private final long time;

    @Override
    public String getStreamName() {
        return "vehicle";
    }

    @Override
    public Object getData() {
        return this;
    }
}
