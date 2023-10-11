package job.join.operator;

import api.Event;
import api.EventCollector;
import api.groupingStrategy.GroupingStrategy;
import api.window.EventWindow;
import api.window.WindowedOperator;
import job.join.event.EmissionEvent;

import java.util.HashMap;
import java.util.Map;

public class WindowedAggregator extends WindowedOperator {
    private int instanceId;

    public WindowedAggregator(final String name, final int parallelism, final GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void apply(final EventWindow window, final EventCollector eventCollector) {
        System.out.println("WindowedAggregator:: instance " + instanceId + "  --> " + window);

        final Map<Integer, Double> emissions = new HashMap<>();

        for (final Event event : window.getEvents()) {
            final EmissionEvent emissionEvent = (EmissionEvent) event;
            final int zoneId = emissionEvent.getZoneId();
            emissions.put(zoneId, emissions.getOrDefault(zoneId, 0.0) + emissionEvent.getEmission());
        }

        System.out.println(window.getStartTime() + " - " + window.getEndTime() + ":\n");
        for (final Map.Entry<Integer, Double> entry : emissions.entrySet()) {
            System.out.println(String.format("%d : %.2f", entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }
}
