import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import java.util.ArrayList;
import java.util.List;

public class TcpClient {

    private static List<NetSocket> sockets = new ArrayList<>(100000);

    public static void main(String[] args) throws InterruptedException {
        Vertx vertx = Vertx.vertx();
        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1);

            createNetClient(vertx, "192.168.77.10");
        }
//        vertx.setPeriodic(10000, l -> {
//            sockets.forEach(socket -> {
//                socket.write("ping");
//            });
//        });
    }

    private static void createNetClient(final Vertx vertx, String host) {
        NetClientOptions netClientOptions = new NetClientOptions().setLocalAddress(host);
        NetClient client = vertx.createNetClient(netClientOptions);
        client.connect(8001, "0.0.0.0", rs -> {
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
