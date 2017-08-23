import io.vertx.core.Vertx;

public class DeployServer {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(TcpServer.class.getName());
    }
}
