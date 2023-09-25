package job.test;

import api.Event;
import api.Operator;
import api.groupingStrategy.FieldsGrouping;
import job.VehicleEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Operator1 extends Operator {
    private int instanceId;
    private final Map<String, Integer> countMap = new HashMap<>();

    public Operator1(final String name) {
        super(name, 2, new FieldsGrouping());
    }

    @Override
    public void apply(final Event event, final List<Event> eventCollector) {
        final String vehicle = ((VehicleEvent) event).getData();
        countMap.put(vehicle, countMap.getOrDefault(vehicle, 0) + 1);

        eventCollector.add(new EventA(vehicle, countMap.get(vehicle)));

        System.out.println("Operator1 :: instance " + instanceId + " --> " + vehicle);
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }
}
