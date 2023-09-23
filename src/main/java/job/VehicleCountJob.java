package job;

import io.javalin.Javalin;

public class VehicleCountJob {
    public static void main(String[] args) {
        Javalin.create()
                .get("/", ctx -> ctx.result("Hello World!"))
                .start(7070);
    }
}
