package job.transaction;

import api.Job;
import api.deliveryStrategy.DeliveryStrategy;
import api.groupingStrategy.ShuffleGrouping;
import engine.JobStarter;
import job.transaction.groupingStrategy.UserAccountFieldsGrouping;
import job.transaction.operators.systemusage.SystemUsageAnalyzer;
import job.transaction.operators.systemusage.SystemUsageWriter;

public class SystemUsageJob {
    public static void main(final String[] args) {
        final Job job = new Job("SystemUsageJob");
        job.addSource(new TransactionSource("TransactionSource", 2, 9990))
                .applyOperator(new SystemUsageAnalyzer("SystemUsageAnalyzer", 2, new UserAccountFieldsGrouping(), DeliveryStrategy.AT_LEAST_ONCE))
                .applyOperator(new SystemUsageWriter("SystemUsageWriter", 1, new ShuffleGrouping(), DeliveryStrategy.AT_LEAST_ONCE));

        new JobStarter(job).start();
    }
}
