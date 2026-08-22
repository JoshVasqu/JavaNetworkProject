import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class HTTPServer {
    private HashMap<String, Socket> clients = new HashMap<>();
    private ServerSocket serverSocket = null;
    private String serverIp = null;
    private int serverPort = 8080;

    public HTTPServer(int serverPort, String serverIp) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void start() throws IOException {
        try {
            serverSocket = new ServerSocket(serverPort, 50, InetAddress.getByName(serverIp));
            System.out.println("Server started");
        } catch(IOException i) {
            System.out.println(i);
        }
    }

    public void run(boolean running) throws IOException{
        if (serverSocket == null) {
            start();
        }

        System.out.println("Waiting for a client ...");
        System.out.println("http://" + serverIp + ":" + serverPort);
        while(running) {
            Socket client = connectClient();
            if (client == null) continue;
            new Thread(() -> handleClient(client));
        }
        disconnectAll();
    }

    private void handleClient(Socket client){
        try {
            printDataFromClient(client);
            sendDataToClient(client);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void sendDataToClient(Socket clientSocket) throws IOException{
        Date today = new Date();
        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
        clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
    }

    public void printDataFromClient(Socket clientSocket) throws IOException{
        InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        String line = reader.readLine();
        while (!line.isEmpty()) {
            System.out.println(line);
            line = reader.readLine();
        }
        isr.close();
        reader.close();
    }

    private Socket connectClient(){
        try {
            Socket clientSocket = serverSocket.accept();
            String clientId = UUID.randomUUID().toString();
            clients.put(clientId, clientSocket);
            return clientSocket;
        } catch (IOException i) {
            System.out.println(i);
            return null;
        }
    }

    private void disconnectClient(Socket clientSocket){
        try {
            clientSocket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    private void disconnectAll() throws IOException{
        for (String key : clients.keySet()) {
            disconnectClient(clients.get(key));
        }
    }

    public static void main(String args[]) throws IOException{
        HTTPServer server = new HTTPServer(8080, "192.168.1.17");
        server.run(true);
    }
}

