package api;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Streams {
    final List<Stream> streams;

    public static Streams of(final Stream ...streams) {
        return new Streams(Arrays.stream(streams).collect(Collectors.toList()));
    }

    public Stream applyOperator(final Operator operator) {
        for (final Stream stream : streams) {
            stream.applyOperator(operator);
        }
        return operator.getOutgoingStream();
    }

}
