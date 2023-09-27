package job.transaction;

import api.Job;
import api.Stream;
import api.Streams;
import engine.JobStarter;
import job.transaction.analyzer.AvgTicketAnalyzer;
import job.transaction.analyzer.WindowedProximityAnalyzer;
import job.transaction.analyzer.WindowedTransactionCountAnalyzer;
import job.transaction.groupingStrategy.TransactionIdFieldsGrouping;
import job.transaction.groupingStrategy.UserAccountFieldsGrouping;

public class FraudDetectionJob {
    public static void main(String[] args) {
        final Job job = new Job("FraudDetectionJob");

        final Stream transactionSoureStream = job.addSource(new TransactionSource("TransactionSource", 2, 9990));
        final Stream avgTicketAnalyzerStream = transactionSoureStream.applyOperator(
                new AvgTicketAnalyzer("AvgTicketAnalyzer", 2, new UserAccountFieldsGrouping())
        );
        final Stream windowedProximityAnalyzerStream = transactionSoureStream.selectChannel("channel1").applyOperator(
                new WindowedProximityAnalyzer("WindowedProximityAnalyzer", 2, new UserAccountFieldsGrouping())
        );
        final Stream windowedTransactionCountAnalyzerStream = transactionSoureStream.applyOperator(
                "channel2",
                new WindowedTransactionCountAnalyzer("WindowedTransactionCountAnalyzer", 2, new UserAccountFieldsGrouping())
        );

        Streams.of(
                avgTicketAnalyzerStream,
                windowedProximityAnalyzerStream,
                windowedTransactionCountAnalyzerStream
        ).applyOperator(
                new ScoreAggregator("ScoreAggregator", 2, new TransactionIdFieldsGrouping())
        );

        new JobStarter(job).start();
    }
}
