package utilities;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Set;

public class ThreadServer extends Thread {

    private final Socket socket;
    private final HashMap<Socket, String> clients;

    public ThreadServer(Socket socket, HashMap<Socket, String> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String outputString = input.readLine();
                if (outputString.equals("logout")) {
                    throw new SocketException();
                }
                if (!clients.containsKey(socket)) {
                    String[] messageString = outputString.split(":", 2);
                    clients.put(socket, messageString[0]);
                    System.out.println(messageString[0] + messageString[1]);
                    showMessageToAllClients(socket, messageString[0] + messageString[1]);
                } else {
                    System.out.println(outputString);
                    showMessageToAllClients(socket, outputString);
                }
            }
        } catch (SocketException e) {
            String printMessage = clients.get(socket) + " left the room";
            System.out.println(printMessage);
            showMessageToAllClients(socket, printMessage);
            clients.remove(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMessageToAllClients(Socket sender, String outputString) {
        PrintWriter printWriter;
        Set<Socket> keySet = clients.keySet();
        for (Socket client : keySet) {
            try {
                if (client != sender) {
                    printWriter = new PrintWriter(client.getOutputStream(), true);
                    printWriter.println(outputString);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}