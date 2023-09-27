package api;

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
}
