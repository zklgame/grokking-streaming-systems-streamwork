package job.join;

import api.Event;
import api.groupingStrategy.FieldsGrouping;
import job.join.event.EmissionEvent;

public class ZoneFieldsGrouping extends FieldsGrouping {
    @Override
    protected Object getKey(final Event event) {
        return ((EmissionEvent) event).getZoneId();
    }
}
