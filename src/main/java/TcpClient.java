import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TcpClient {

    private static List<NetSocket> sockets = new ArrayList<>(1000000);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Stream.of("192.168.99.10", "192.168.99.11", "192.168.99.12", "192.168.99.13", "192.168.99.14", "192.168.99.15", "192.168.99.16", "192.168.99.17", "192.168.99.18", "192.168.99.19",
            "192.168.88.10", "192.168.88.11", "192.168.88.12", "192.168.88.13", "192.168.88.14", "192.168.88.15", "192.168.88.16", "192.168.88.17", "192.168.88.18", "192.168.88.19").forEach(localAddress -> {
            for (int i = 0; i < 50000; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                createNetClient(vertx, "127.0.0.1", localAddress);
            }
        });
    }

    private static void createNetClient(final Vertx vertx, final String server, final String local) {
        final NetClientOptions opts = new NetClientOptions();
        opts.setLocalAddress(local);
        NetClient client = vertx.createNetClient(opts);
        client.connect(8001, server, rs -> {
            if (rs.succeeded()) {
                NetSocket socket = rs.result();

                sockets.add(socket);

                socket.handler(buf -> {
                    System.out.println(buf.toString());
                });
            }
        });
    }
}
