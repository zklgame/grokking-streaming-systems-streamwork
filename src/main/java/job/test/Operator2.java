package job.test;

import api.Event;
import api.Operator;
import api.groupingStrategy.FieldsGrouping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Operator2 extends Operator {
    private int instanceId;

    public Operator2(final String name) {
        super(name, 2, new FieldsGrouping());
    }

    @Override
    public void apply(final Event event, final List<Event> eventCollector) {
        final Map<String, Integer> map = ((EventA) event).getData();
        System.out.println("Operator2 :: instance " + instanceId + " --> " + new ArrayList<>(map.keySet()).get(0) + " : " + new ArrayList<>(map.values()).get(0));
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }
}