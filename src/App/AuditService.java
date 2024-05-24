package App;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static AuditService instance = null;
    private static final String filePath;
    private final File file;

    static {
        filePath = "audit.csv";
    }

    private AuditService() {
        this.file = new File(filePath);
        boolean fileExists = this.file.exists();

        try {
            FileWriter fileWriter = new FileWriter(this.file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // write
            if (!fileExists) {
                bufferedWriter.write("Data, Ora, Nume, Prenume, Actiune");
                bufferedWriter.newLine();
            }

            // close
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println("FAILED -> init audit service");
            System.out.println(e.getMessage());
        }
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }

        return instance;
    }

    public void writeAction(String nume, String prenume, String action) {
        if (file == null) {
            return; // TODO: throw custom exception
        }

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            StringBuilder line = new StringBuilder();
            line.append(currentDate).append(",");
            line.append(currentTime).append(",");
            line.append(nume).append(",");
            line.append(prenume).append(",");
            line.append(action).append(",");

            // write
            bufferedWriter.write(line.toString());
            bufferedWriter.newLine();

            // close
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println("FAILED: audit service");
            System.out.println(e.getMessage());
        }
    }
}

