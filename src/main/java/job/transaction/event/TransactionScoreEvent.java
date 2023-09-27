package job.transaction.event;

import api.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class TransactionScoreEvent extends Event {
    private final TransactionEvent transactionEvent;
    private final float score;

    @Override
    public Object getData() {
        return this;
    }
}
