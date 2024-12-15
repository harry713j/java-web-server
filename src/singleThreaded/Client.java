package singleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public void run() throws UnknownHostException, IOException {
        int port = 8080;
        InetAddress address = InetAddress.getByName("localhost");
        Socket socket = new Socket(address, port);

        System.out.println("Client Local IP Address: " + InetAddress.getLocalHost().getHostAddress());

        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toSocket.println("Hello from the Client" + socket.getLocalSocketAddress());
        String line = fromSocket.readLine();

        System.out.println("Server IP address: " + socket.getInetAddress().getHostAddress());
        System.out.println("Response from the socket/server is : " + line);

        toSocket.close();
        fromSocket.close();
        socket.close();
    }
    public static void main(String[] args) {
        Client client = new Client();

        try{
            client.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
