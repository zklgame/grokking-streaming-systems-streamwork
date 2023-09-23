package engine;

import api.Job;
import io.javalin.Javalin;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JobStarter {
    private final Job job;

    public void start() {
        System.out.println(job.getName());
        Javalin.create()
                .get("/", ctx -> ctx.result("Hello World!"))
                .start(7070);
    }
}
