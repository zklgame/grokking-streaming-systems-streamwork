package job.vehicle;

import api.Job;
import api.groupingStrategy.FieldsGrouping;
import engine.JobStarter;

public class VehicleCountJob {
    public static void main(String[] args) {
        final Job job = new Job("VehicleCountJob");
        job.addSource(new SensorReader("SensorReader", 2, 9990))
                .applyOperator(new VehicleCounter("VehicleCounter", 2, new FieldsGrouping()));

        System.out.println("This is a streaming job that counts vehicles in real time. " +
                "Please enter vehicle types like 'car' and 'truck' in the input terminal " +
                "and look at the output");

        new JobStarter(job).start();
    }
}
