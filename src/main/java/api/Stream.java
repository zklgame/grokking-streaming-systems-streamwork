package api;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Stream {
    private final Set<Operator> operatorSet = new HashSet<>();

    public Stream applyOperator(final Operator operator) {
        if (operatorSet.contains(operator)) {
            throw new RuntimeException("Operator " + operator.getName() + " has been applied before!");
        }
        operatorSet.add(operator);

        return operator.getOutgoingStream();
    }
}