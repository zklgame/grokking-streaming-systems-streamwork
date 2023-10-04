package api.window;

import api.Stream;
import api.window.strategy.WindowStrategy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StreamWindow {
    private final WindowStrategy strategy;
    private final Stream stream;

    public Stream applyOperator(final WindowedOperator operator) {
        return stream.applyWindowOperator(strategy, operator);
    }
}
