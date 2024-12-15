package multiThreaded;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    // functional interface Consumer we can directly pass to Thread
    public Consumer<Socket> getConsumer() {
//        return new Consumer<Socket>() {
//            @Override
//            public void accept(Socket socket) {
//                try{
//                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
//                toClient.println("Hello from the Server " + clientSocket.getInetAddress());
//
//                toClient.close();
//            }catch (IOException e){
//                return;
//            }
//            }
//        };

        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);

                try(BufferedReader fromFile = new BufferedReader(new FileReader("c:\\Users\\91859\\Desktop\\Projects\\java-projects\\Java-web-server\\src\\message.txt"));) {
                    String line;
                    while ((line = fromFile.readLine()) != null){
                        toClient.println(line);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                toClient.close();
            } catch (IOException e) {
                return;
            }
        };
    }

    public static void main(String[] args) {
        int port = 8080;
        Server server = new Server();

        try (ServerSocket socket = new ServerSocket(port);) {
            socket.setSoTimeout(20000);
            System.out.println("Server is listening on the port: " + port);
            while (true) {
                Socket clientSocket = socket.accept();

                Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Traditional way:
// public class Server {
//    // Traditional Runnable implementation for handling client connections
//    private class ClientHandler implements Runnable {
//        private Socket clientSocket;
//
//        // Constructor to pass the socket to the handler
//        public ClientHandler(Socket socket) {
//            this.clientSocket = socket;
//        }
//
//        @Override
//        public void run() {
//            try {
//                // Handle client connection logic
//                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
//                toClient.println("Hello from the Server " + clientSocket.getInetAddress());
//
//                // Optional: Add more processing here if needed
//
//                // Close resources
//                toClient.close();
//                clientSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void startServer() {
//        int port = 8080;
//
//        try (ServerSocket serverSocket = new ServerSocket(port)) {
//            serverSocket.setSoTimeout(20000);
//
//            while (true) {
//                System.out.println("Server is listening on the port: " + port);
//
//                // Accept incoming client connection
//                Socket clientSocket = serverSocket.accept();
//
//                // Create a new thread for each client connection
//                // Using the traditional Runnable approach
//                Thread thread = new Thread(new ClientHandler(clientSocket));
//                thread.start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        Server server = new Server();
//        server.startServer();
//    }
//}
