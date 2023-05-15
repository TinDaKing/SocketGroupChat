import utilities.ThreadClient;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.print("Enter your name to enter the chat room: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        try (Socket socket = new Socket("localhost", 3702)) {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            ThreadClient threadClient = new ThreadClient(socket); // to receive messages from others
            new Thread(threadClient).start();

            printWriter.println(name + ": has joined chat room.");
            while(true) {
                String message = scanner.nextLine();
                if (message.equals("logout")) {
                    printWriter.println("logout");
                    break;
                }
                printWriter.println(name+": " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}