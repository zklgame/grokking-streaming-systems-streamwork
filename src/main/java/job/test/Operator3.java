package job.test;

import api.Event;
import api.Operator;
import api.groupingStrategy.FieldsGrouping;
import job.VehicleEvent;

import java.util.List;

public class Operator3 extends Operator {
    private int instanceId;

    public Operator3(final String name) {
        super(name, 2, new FieldsGrouping());
    }

    @Override
    public void apply(final Event event, final List<Event> eventCollector) {
        final String vehicle = ((VehicleEvent) event).getData();

        eventCollector.add(new EventB(vehicle.equals("car")));

        System.out.println("Operator3 :: instance " + instanceId + " --> " + vehicle);
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }
}