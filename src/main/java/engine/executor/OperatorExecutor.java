package engine.executor;

import api.Operator;
import api.groupingStrategy.GroupingStrategy;
import org.apache.commons.lang3.SerializationUtils;


public class OperatorExecutor extends ComponentExecutor {
    private final Operator operator;

    public OperatorExecutor(final Operator operator) {
        super(operator);
        this.operator = operator;

        for (int i = 0; i < operator.getParallelism(); i ++) {
            final Operator cloned = SerializationUtils.clone(operator);
            instanceExecutors[i] = new OperatorInstanceExecutor(cloned, i);
        }
    }

    public GroupingStrategy getGroupingStrategy() {
        return operator.getGroupingStrategy();
    }
}
