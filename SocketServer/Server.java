import utilities.ThreadServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    public static void main(String[] args) {
        HashMap<Socket, String> clients = new HashMap<>();
        try (ServerSocket serversocket = new ServerSocket(3702)) {
            System.out.println("Server is started...");
            while (true) {
                Socket socket = serversocket.accept();
                ThreadServer ThreadServer = new ThreadServer(socket, clients);
                ThreadServer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}