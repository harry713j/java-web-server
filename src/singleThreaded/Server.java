package singleThreaded;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    /*
        1. A web server listen on a port through a socket
        2. When client try to request something to web server (TCP), first it establishes the
           connection with the server with 3-way handshakes.
        3. After the connection is established the server opens a separate socket for that client
           through which they send and receive the data. (Data sends in bytes)
    * */
    public void run() throws IOException {
        int port = 8080;
        // ServerSocket: This class implements server sockets. A server socket waits for
        // requests to come in over the network. It performs some operation based on that request,
        // and then possibly returns a result to the requester.
        ServerSocket socket = new ServerSocket(port);
        socket.setSoTimeout(20000);

        System.out.println("Server IP Address: " + InetAddress.getLocalHost().getHostAddress());
        while(true){
            System.out.println("Server is listening on port number " + port);
            // Socket: This class implements client sockets (also called just "sockets").
            // A socket is an endpoint for communication between two machines.
            Socket acceptedSocket = socket.accept();
            System.out.println("Connected to " + acceptedSocket.getRemoteSocketAddress());
            // Write to the client
            PrintWriter toClient = new PrintWriter(acceptedSocket.getOutputStream(), true);
            // Read what is coming from client
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()));

            try(BufferedReader fromFile = new BufferedReader(new FileReader("c:\\Users\\91859\\Desktop\\Projects\\java-projects\\Java-web-server\\src\\message.txt"));) {
                String line;
                while ((line = fromFile.readLine()) != null){
                    toClient.println(line);
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            fromClient.close();
            toClient.close();
            acceptedSocket.close();
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
