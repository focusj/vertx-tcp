import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.SocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class TcpServer extends AbstractVerticle {
    private static AtomicInteger counter = new AtomicInteger(1);

    @Override public void start(final Future<Void> startFuture) throws Exception {
        try {
            NetServerOptions opts = new NetServerOptions();

            NetServer netServer = vertx.createNetServer(opts);

            netServer.connectHandler(socket -> {
                String address = socket.remoteAddress().host() + ":" + socket.remoteAddress().port();
                System.out.println(counter.getAndAdd(1) + " connection " + "[" + address + "]" + " connects in...");

                socket.handler(buffer -> {
                    socket.write("pong");
                });
            });

            netServer.listen(8001);

            startFuture.succeeded();
        } catch (Exception e) {
            startFuture.fail(e);
        }
    }
}
