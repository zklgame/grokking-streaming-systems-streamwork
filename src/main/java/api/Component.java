package api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public abstract class Component implements Serializable {
    private final String name;
    private final int parallelism;
    private final Stream outgoingStream = new Stream();

    public abstract void setupInstance(final int instanceId);
}
