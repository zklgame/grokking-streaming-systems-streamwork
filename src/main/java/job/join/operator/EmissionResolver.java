package job.join.operator;

import api.Event;
import api.EventCollector;
import api.Operator;
import job.join.event.EmissionEvent;
import job.join.event.VehicleTemperatureEvent;

import java.util.Map;

public class EmissionResolver extends Operator {
    private int instanceId;

    private final Map<String, Double> vehicleToEmissionMap = Map.of(
            "XXX:AA:2020:90", 3.3,
            "YYY:CC:2021:90", 4.2
    );

    public EmissionResolver(final String name, final int parallelism) {
        super(name, parallelism);
    }

    @Override
    public void apply(final Event event, final EventCollector eventCollector) {
        final VehicleTemperatureEvent vehicleTemperatureEvent = (VehicleTemperatureEvent) event;

        final Double emission = vehicleToEmissionMap.getOrDefault(
                vehicleTemperatureEvent.getMake() + ":" + vehicleTemperatureEvent.getModel() + ":" + vehicleTemperatureEvent.getYear() + ":" + Math.round(vehicleTemperatureEvent.getTemperature()), 4.0
        );

        final EmissionEvent emissionEvent = new EmissionEvent(vehicleTemperatureEvent.getZoneId(), emission, vehicleTemperatureEvent.getTime());

        System.out.println("\nEmissionResolver :: instance " + instanceId + " --> " + emissionEvent);

        eventCollector.add(emissionEvent);
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }
}
