import javafx.application.Application;
import server.ServerLauncher;

public class Launcher {
    public static void main(String[] args) {
        //ServerLauncher.main(args);
        System.setProperty("javafx.platform", "monocle");
        Application.launch(ServerLauncher.class, args);
    }
}
