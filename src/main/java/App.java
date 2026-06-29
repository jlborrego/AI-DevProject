import io.javalin.Javalin;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.routes.get("/health", ctx -> {
                ctx.contentType("application/json");
                ctx.json(Map.of("status", "UP"));
            });
        });

        app.start(7000);
        System.out.println("Server started on http://localhost:7000");
    }
}
