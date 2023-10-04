package api;

import api.window.InternalWindowedOperator;
import api.window.StreamWindow;
import api.window.WindowedOperator;
import api.window.strategy.WindowStrategy;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class Stream implements Serializable {
    private final Map<String, Set<Operator>> operatorMap = new HashMap<>();

    public static final String DEFAULT_CHANNEL = "default";

    public Stream applyOperator(final Operator operator) {
        return applyOperator(DEFAULT_CHANNEL, operator);
    }

    public Stream applyOperator(final String channel, final Operator operator) {
        final Set<Operator> operatorSet = operatorMap.getOrDefault(channel, new HashSet<>());

        if (operatorSet.contains(operator)) {
            throw new RuntimeException("Operator " + operator.getName() + " has been applied before!");
        }
        operatorSet.add(operator);

        operatorMap.put(channel, operatorSet);

        return operator.getOutgoingStream();
    }

    public StreamChannel selectChannel(final String channel) {
        return new StreamChannel(channel, this);
    }

    // for window

    public StreamWindow withWindowing(final WindowStrategy strategy) {
        return new StreamWindow(strategy, this);
    }

    // ignore channel for now
    public Stream applyWindowOperator(final WindowStrategy strategy, final WindowedOperator operator) {
        final InternalWindowedOperator internalWindowOperator = new InternalWindowedOperator(operator, strategy);

        applyOperator(internalWindowOperator);

        return operator.getOutgoingStream();
    }
}
