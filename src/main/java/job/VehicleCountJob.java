package job;

import api.Job;
import engine.JobStarter;

public class VehicleCountJob {
    public static void main(String[] args) {
        final Job job = new Job("VehicleCountJob");
        job.addSource(new SensorReader("SensorReader", 9999))
                .applyOperator(new VehicleCounter("VehicleCounter"));

        System.out.println("This is a streaming job that counts vehicles in real time. " +
                "Please enter vehicle types like 'car' and 'truck' in the input terminal " +
                "and look at the output");

        new JobStarter(job).start();
    }
}
