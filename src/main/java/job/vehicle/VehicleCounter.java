package job.vehicle;

import api.Event;
import api.EventCollector;
import api.Operator;
import api.groupingStrategy.GroupingStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VehicleCounter extends Operator {
    private int instanceId;

    private final Map<String, Integer> countMap = new HashMap<>();

    public VehicleCounter(final String name, final int parallelism) {
        super(name, parallelism);
    }

    public VehicleCounter(final String name, final int parallelism, final GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void apply(final Event event, final EventCollector eventCollector) {
        final String vehicle = ((VehicleEvent) event).getData();
        countMap.put(vehicle, countMap.getOrDefault(vehicle, 0) + 1);

        System.out.println("VehicleCounter:: instance " + instanceId + "  -->");
        printCountMap();
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }

    private void printCountMap() {
        final List<String> vehicles = countMap.keySet().stream().sorted().collect(Collectors.toList());
        vehicles.forEach(vehicle -> {
            System.out.println(" " + vehicle + " : " + countMap.get(vehicle));
        });
    }
}
