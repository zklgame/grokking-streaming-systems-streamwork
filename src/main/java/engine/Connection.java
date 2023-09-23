package engine;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Connection {
    private final ComponentExecutor from;
    private final OperatorExecutor to;
}
