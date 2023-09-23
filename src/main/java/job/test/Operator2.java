package job.test;

import api.Event;
import api.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Operator2 extends Operator {
    public Operator2(final String name) {
        super(name);
    }

    @Override
    public void apply(final Event event, final List<Event> eventCollector) {
        final Map<String, Integer> map = ((EventA) event).getData();
        System.out.println("Operator2 --> " + new ArrayList<>(map.keySet()).get(0) + " : " + new ArrayList<>(map.values()).get(0));
    }
}