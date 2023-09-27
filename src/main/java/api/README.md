# Job

The `Job` class is used by users to set up their jobs and run.

```java
Job job = new Job("job");
Stream stream1 = job.addSource(source1)
   .selectChannel("channel1")
   .applyOperator(operator1);
Stream stream2 = job.addSource(source2)
   .applyOperator("channel2", operator2)
Streams.of(stream1, stream2).applyOperator(operator3);
```

# Component

Components include `Source` and `Operator`.
An `Stream` object is used to connect the downstream operators.

# Source

`Source` accepts events from external into the system.

# Operator

`Operator` applies logic to the incoming event and generate results.

# Stream

`Stream` represents a data stream coming out of a `Component`.
`Operator` with the correct type can be applied to this stream.

# Event

Data block.