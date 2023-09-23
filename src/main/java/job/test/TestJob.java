package job.test;

import api.Job;
import engine.JobStarter;
import job.SensorReader;

public class TestJob {
    public static void main(String[] args) {
        final Job job = new Job("TestJob");
        job.addSource(new SensorReader("SensorReader", 9999))
                .applyOperator(new Operator1("Operator1"))
                .applyOperator(new Operator2("Operator2"));
        job.addSource(new SensorReader("SensorReader", 9998))
                .applyOperator(new Operator3("Operator3"))
                .applyOperator(new Operator4("Operator4"));

        new JobStarter(job).start();
    }
}
