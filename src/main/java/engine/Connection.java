package engine;

import engine.executor.ComponentExecutor;
import engine.executor.OperatorExecutor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Connection {
    private final ComponentExecutor from;
    private final OperatorExecutor to;
}
