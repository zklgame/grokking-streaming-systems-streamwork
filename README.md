To run it by IDE.

This project cannot use gradle to run due to Javalin error.

```shell
export JAVA_HOME=/Library/Java/JavaVirtualMachines/amazon-corretto-11.jdk/Contents/Home
./gradlew clean build; java -cp build/libs/streamwork-1.0-SNAPSHOT.jar job.vehicle.VehicleCountJob
```