package job.transaction.groupingStrategy;

import api.Event;
import api.groupingStrategy.FieldsGrouping;
import job.transaction.event.TransactionScoreEvent;

public class TransactionIdFieldsGrouping extends FieldsGrouping {
    @Override
    protected Object getKey(final Event event) {
        return ((TransactionScoreEvent) event).getTransactionEvent().getTransactionId();
    }
}
