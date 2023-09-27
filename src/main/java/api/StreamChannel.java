package api;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StreamChannel {
    private final String channel;
    private final Stream stream;

    public Stream applyOperator(final Operator operator) {
        return stream.applyOperator(channel, operator);
    }
}
