package job.test;

import api.Event;
import api.Operator;
import job.VehicleEvent;

import java.util.List;

public class Operator3 extends Operator {
    public Operator3(final String name) {
        super(name);
    }

    @Override
    public void apply(final Event event, final List<Event> eventCollector) {
        final String vehicle = ((VehicleEvent) event).getData();

        eventCollector.add(new EventB(vehicle.equals("car")));

        System.out.println("Operator3 --> " + vehicle);
    }
}