package engine;

import api.Component;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WebServer {
    public void start(final List<Connection> connectionList) {
        Javalin.create()
                .get("/", ctx -> index(ctx, connectionList))
                .start(7070);
    }

    private void index(final Context ctx, final List<Connection> connectionList) {
        final Map<ComponentExecutor, Set<ComponentExecutor>> graph = new HashMap<>();
        final Set<ComponentExecutor> toSet = new HashSet<>();

        for (final Connection connection : connectionList) {
            final Set<ComponentExecutor> graphToSet = graph.getOrDefault(connection.getFrom(), new HashSet<>());
            graphToSet.add(connection.getTo());
            graph.put(connection.getFrom(), graphToSet);

            toSet.add(connection.getTo());
        }

        final Set<ComponentExecutor> sourceSet = new HashSet<>();
        for (final ComponentExecutor componentExecutor : graph.keySet()) {
            if (!toSet.contains(componentExecutor)) {
                sourceSet.add(componentExecutor);
            }
        }

        String str = "";
        for (final ComponentExecutor executor : sourceSet) {
            str += traverse(graph, executor, 0);
        }

        ctx.result(str);
    }

    private String traverse(final Map<ComponentExecutor, Set<ComponentExecutor>> graph, final ComponentExecutor executor, final int level) {
        String str = "";
        for (int i = 0; i < level; i++) {
            str += " ";
        }

        str += executor.getComponent().getName() + "\n";

        for (final ComponentExecutor componentExecutor : graph.getOrDefault(executor, new HashSet<>())) {
            str += traverse(graph, componentExecutor, level + 1);
        }

        return str;
    }
}
