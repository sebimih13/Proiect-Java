import App.AppService;
import App.Database;
import App.AuditService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting application...");

        // init App.Database
        Database.getInstance();

        // init App.AuditService
        AuditService.getInstance();

        // init App.AppService
        AppService.getInstance();

        // run App.AppService
        AppService.getInstance().run();
    }
}

