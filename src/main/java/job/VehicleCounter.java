package job;

import api.Event;
import api.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VehicleCounter extends Operator {
    private final Map<String, Integer> countMap = new HashMap<>();

    public VehicleCounter(final String name) {
        super(name);
    }

    @Override
    public void apply(final Event event, final List<Event> eventCollector) {
        final String vehicle = ((VehicleEvent) event).getData();
        countMap.put(vehicle, countMap.getOrDefault(vehicle, 0) + 1);

        System.out.println("VehicleCounter -->");
        printCountMap();
    }

    private void printCountMap() {
        final List<String> vehicles = countMap.keySet().stream().sorted().collect(Collectors.toList());
        vehicles.forEach(vehicle -> {
            System.out.println(" " + vehicle + " : " + countMap.get(vehicle));
        });
    }
}
