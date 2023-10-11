package job.join.event;

import api.window.TimedEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class EmissionEvent extends TimedEvent {
    private final int zoneId;
    private final double emission;
    private final long time;

    @Override
    public Object getData() {
        return this;
    }
}
