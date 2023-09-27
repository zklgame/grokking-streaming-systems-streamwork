package job.transaction.groupingStrategy;

import api.Event;
import api.groupingStrategy.FieldsGrouping;
import job.transaction.event.TransactionEvent;

public class UserAccountFieldsGrouping extends FieldsGrouping {
    @Override
    protected Object getKey(final Event event) {
        return ((TransactionEvent) event).getUserAccount();
    }
}
