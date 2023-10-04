package job.window;

import api.Job;
import api.window.strategy.FixedWindowStrategy;
import engine.JobStarter;
import job.transaction.TransactionSource;
import job.transaction.groupingStrategy.UserAccountFieldsGrouping;

public class WindowTestJob {
    public static void main(final String[] args) {
        final Job job = new Job("WindowTestJob");
        job.addSource(new TransactionSource("TransactionSource", 1, 9990))
                .withWindowing(new FixedWindowStrategy(5 * 1000, 1 * 1000))
                .applyOperator(new WindowedAnalyzer("WindowedAnalyzer", 1, new UserAccountFieldsGrouping()));

        new JobStarter(job).start();
    }
}
