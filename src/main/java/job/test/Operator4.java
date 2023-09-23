package job.test;

import api.Event;
import api.Operator;

import java.util.List;

public class Operator4 extends Operator {
    public Operator4(final String name) {
        super(name);
    }

    @Override
    public void apply(final Event event, final List<Event> eventCollector) {
        final boolean isCar = ((EventB) event).getData();

        System.out.println("Operator4 --> " + isCar);
    }
}