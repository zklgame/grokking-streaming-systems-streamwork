package api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Component {
    private final String name;
    private final Stream outgoingStream = new Stream();
}
