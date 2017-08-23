import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TcpClient {

    private static List<NetSocket> sockets = new ArrayList<>(100000);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Stream.of("192.168.31.180", "192.168.31.181", "192.168.31.182", "192.168.31.183", "192.168.31.184",
            "192.168.31.185", "192.168.31.186", "192.168.31.187", "192.168.31.188", "192.168.31.189"
        ).forEach(localAddress -> {
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
