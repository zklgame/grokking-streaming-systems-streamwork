package api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class Job {
    private final String name;
    private final Set<Source> sourceSet = new HashSet<>();

    public Stream addSource(final Source source) {
        if (sourceSet.contains(source)) {
            throw new RuntimeException("Source " + source.getName() + " has been added into Job " + name + " before!");
        }
        sourceSet.add(source);

        return source.getOutgoingStream();
    }
}
