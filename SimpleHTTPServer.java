import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.io.File;
import java.nio.file.Path;

/** 
 * https://javarevisited.blogspot.com/2015/06/how-to-create-http-server-in-java-serversocket-example.html
 * 
 * Originally java program to create a simple HTTP server to demonstrate how to use
 * ServerSocket and Socket class.
 * 
 * Modified by 
 * 
 * @author Javin Paul (original)
 * @author Joshua Vasquez (modifications)
 */

public class SimpleHTTPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        while (true) {
            try (Socket socket = server.accept()) {
                
                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK \r\n\r\n" + today;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                String line = reader.readLine();
                while (!line.isEmpty()) {
                    
                }
            }

            /*
            Socket clientSocket = server.accept();
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }
            */
        }
    }
}

// ---------------------------------------------------------------------------

public enum LogLevel {
    INFO(0),
    ERROR(1),
    FATAL(2);

    private final int level;

    LogLevel(int level) {
        this.level = level;
    }

    public boolean isEqual(LogLevel comparison) {
        return this.level == comparison.level;
    }

    public boolean isLess(LogLevel comparison) {
        return this.level < comparison.level;
    }

    public boolean isGreater(LogLevel comparison) {
        return this.level > comparison.level;
    }
}

// ---------------------------------------------------------------------------

public interface LogAppender {
    void append(String formattedMessage) throws IOException;
    void close() throws IOException;
}

// ---------------------------------------------------------------------------

public class ServerLogging {
    private String path;

    public ServerLogging(String path) {
        if (path == null || path.isEmpty()) {
            this.path = "log/";
        } else {
            this.path = path;
        }
    }
    
    public void createDestination() {

    }
}


public static void createLog(int num) {
    if(num >= 50) {
        System.out.println("Logging is over 50 files currently.");
    }

    try {
        File myObj = new File("Log"+num+".txt");
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
        } else {
            createLog(num + 1);
        }
    } catch (IOException e) {
        System.out.println("Logging error occurred.");
        e.printStackTrace(); 
    }
}