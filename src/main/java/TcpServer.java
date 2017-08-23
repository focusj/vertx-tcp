import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

import java.util.concurrent.atomic.AtomicInteger;

public class TcpServer extends AbstractVerticle {
    private static AtomicInteger counter = new AtomicInteger(1);

    @Override public void start(final Future<Void> startFuture) throws Exception {
        try {
            NetServerOptions opts = new NetServerOptions();

            NetServer netServer = vertx.createNetServer(opts);

            netServer.connectHandler(socket -> {
                String address = socket.remoteAddress().host() + ":" + socket.remoteAddress().port();
                final int i = counter.getAndAdd(1);
                if (i % 1000 == 0) {
                    System.out.println(i + " connections connects in...");
                }

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
