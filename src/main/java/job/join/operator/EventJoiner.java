package job.join.operator;

import api.Event;
import api.EventCollector;
import api.JoinOperator;
import api.groupingStrategy.GroupingStrategy;
import job.join.event.TemperatureEvent;
import job.join.event.VehicleEvent;
import job.join.event.VehicleTemperatureEvent;


import java.util.HashMap;
import java.util.Map;

public class EventJoiner extends JoinOperator {
    private int instanceId;

    private final Map<Integer, Float> zoneToTemperatureMap = new HashMap<>();

    public EventJoiner(final String name, final int parallelism, final Map<String, GroupingStrategy> nameToGroupingStrategyMap) {
        super(name, parallelism, nameToGroupingStrategyMap);
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public void apply(final Event event, final EventCollector eventCollector) {
        System.out.println("\nEventJoiner :: instance " + instanceId + " --> received: " + event);

        if (event.getStreamName().equals("temperature")) {
            final TemperatureEvent temperatureEvent = (TemperatureEvent) event;
            zoneToTemperatureMap.put(temperatureEvent.getZoneId(), temperatureEvent.getTemperature());
        } else if (event.getStreamName().equals("vehicle")) {
            final VehicleEvent vehicleEvent = (VehicleEvent) event;

            final VehicleTemperatureEvent vehicleTemperatureEvent = new VehicleTemperatureEvent(
                    vehicleEvent.getMake(), vehicleEvent.getModel(), vehicleEvent.getYear(), vehicleEvent.getZoneId(),
                    zoneToTemperatureMap.containsKey(vehicleEvent.getZoneId()) ? zoneToTemperatureMap.get(vehicleEvent.getZoneId()) : 60,
                    vehicleEvent.getTime()
            );

            System.out.println("\nEventJoiner :: instance " + instanceId + " --> emit: " + vehicleTemperatureEvent);

            eventCollector.add(vehicleTemperatureEvent);
        }
    }
}
