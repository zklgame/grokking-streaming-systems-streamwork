package job.transaction.event;

import api.Event;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class SystemUsageEvent extends Event {
    private final int transactionCount;
    private final int fraudTransactionCount;
}
