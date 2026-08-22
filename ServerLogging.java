
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


/*
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
*/