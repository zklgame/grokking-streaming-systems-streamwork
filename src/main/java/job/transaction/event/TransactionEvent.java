package job.transaction.event;

import api.window.TimedEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@RequiredArgsConstructor
@Getter
@ToString
public class TransactionEvent extends TimedEvent {
    private final String transactionId;
    private final float amount;
    private final Date transactionTime;
    private final long merchandiseId;
    private final long userAccount;

    @Override
    public Object getData() {
        return this;
    }

    @Override
    public long getTime() {
        return transactionTime.toInstant().toEpochMilli();
    }
}
