package job.join;

import api.Job;
import api.Stream;
import api.Streams;
import api.groupingStrategy.AllGrouping;
import api.groupingStrategy.ShuffleGrouping;
import api.window.strategy.FixedWindowStrategy;
import engine.JobStarter;
import job.join.operator.EmissionResolver;
import job.join.operator.EventJoiner;
import job.join.operator.WindowedAggregator;
import job.join.source.TemperatureEventSource;
import job.join.source.VehicleEventSource;

import java.util.Map;

public class EmissionJob {
    public static void main(final String[] args) {
        final Job job = new Job("EmissionJob");

        final Stream vehicleEventStream = job.addSource(new VehicleEventSource("VehicleEventSource", 1, 9990));
        final Stream temperatureEventStream = job.addSource(new TemperatureEventSource("TemperatureEventSource", 1, 9992));

        Streams.of(vehicleEventStream, temperatureEventStream)
                .applyOperator(new EventJoiner("EventJoiner", 2, Map.of("vehicle", new ShuffleGrouping(), "temperature", new AllGrouping())))
                .applyOperator(new EmissionResolver("EmissionResolver", 1))
                .withWindowing(new FixedWindowStrategy(5000, 2000))
                .applyOperator(new WindowedAggregator("WindowedAggregator", 2, new ZoneFieldsGrouping()));

        new JobStarter(job).start();
    }
}
