package api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EventCache {
    private final Event event;
    private int resendCount = 0;

    public void addResendCount() {
        resendCount += 1;
    }
}
