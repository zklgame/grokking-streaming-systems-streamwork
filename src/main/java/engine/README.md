# JobStarter

Builds a graph with `ComponentExecutor` and `Connection` derived from `Job` definition.

# EventQueue

`EventQueue` is the base class for intermediate event queues between processes.

# Process / ComponentExecutor

`Process` is the base class of all processes (executors). When a process is started, a new thread is created to call the `runOnce()` function of the derived class.

`ComponentExecutor` is the base class for executors of `Source` and `Operator`, it has an incoming `EventQueue` and an outgoing `EventQueue`.

# SourceExecutor

When a `SourceExecutor` is started, a new thread is created to call the `getEvents()` function of the `Source` component repeatedly.

# OperatorExecutor

When a `OperatorExecutor` is started, a new thread is created to call the `apply()` function of the `Operator` component repeatedly.

# Connection

Connects `ComponentExecutor` (from) and `OperatorExecutor` (to).

# WebServer

Optional. To visualize the built graph.