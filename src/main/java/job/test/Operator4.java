package job.test;

import api.Event;
import api.Operator;
import api.groupingStrategy.FieldsGrouping;

import java.util.List;

public class Operator4 extends Operator {
    private int instanceId;

    public Operator4(final String name) {
        super(name, 2, new FieldsGrouping());
    }

    @Override
    public void apply(final Event event, final List<Event> eventCollector) {
        final boolean isCar = ((EventB) event).getData();

        System.out.println("Operator4 :: instance " + instanceId + " --> " + isCar);
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }
}