package job.join.event;

import api.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class TemperatureEvent extends Event {
    private final float temperature;
    private final int zoneId;

    @Override
    public String getStreamName() {
        return "temperature";
    }

    @Override
    public Object getData() {
        return this;
    }
}
