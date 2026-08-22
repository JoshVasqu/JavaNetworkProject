import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class HTTPServer {
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;
    private String serverIp = null;

    public HTTPServer(int port, String serverIp) {
        // start server and wait for connection
        try {
            serverSocket = new ServerSocket(port, 50, InetAddress.getByName(serverIp));
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            System.out.println("http://" + serverIp + ":" + port);

            // connection
            clientSocket = serverSocket.accept();
            System.out.println("Client accepted");

            // send data to client
            Date today = new Date();
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
            clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));

            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }
            isr.close();
            reader.close();
            
            System.out.println("Closing connection");

            clientSocket.close();
        } catch(IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        HTTPServer server = new HTTPServer(8080, "192.168.1.17");
    }
}
