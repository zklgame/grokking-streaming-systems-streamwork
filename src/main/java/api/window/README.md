```java
Job job = new Job("job");
job.addSource(source)
   .withWindowing(new FixedWindowStrategy(1000, 1000))
   .applyOperator(windowOperator);
```