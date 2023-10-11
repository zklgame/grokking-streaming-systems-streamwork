package api;

import api.groupingStrategy.GroupingStrategy;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class JoinOperator extends Operator {
    private final Map<String, GroupingStrategy> streamNameToGroupingStrategyMap;

    public JoinOperator(final String name, final int parallelism, final Map<String, GroupingStrategy> streamNameToGroupingStrategyMap) {
        super(name, parallelism);
        this.streamNameToGroupingStrategyMap = streamNameToGroupingStrategyMap;
    }

    public GroupingStrategy getGroupingStrategy(final String name) {
        return streamNameToGroupingStrategyMap.get(name);
    }
}
