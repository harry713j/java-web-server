package threadPool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService threadPool;

    public Server(int poolSize){
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket socket){
        try(PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true)){
            try(BufferedReader fromFile = new BufferedReader(new FileReader("c:\\Users\\91859\\Desktop\\Projects\\java-projects\\Java-web-server\\src\\message.txt"));) {
                String line;
                while ((line = fromFile.readLine()) != null){
                    toClient.println(line);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        int poolSize = 100;
        Server server = new Server(poolSize);

        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(20000);
            System.out.println("Server is listening on port: " + port);

            while (true){
                Socket clientSocket = serverSocket.accept();

                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
