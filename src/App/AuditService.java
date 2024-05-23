package App;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    static private final String filePath;

    static {
        filePath = "audit.csv";
    }

    static public void writeAction(String action) {
        try {
            File file = new File(filePath);
            boolean fileExists = file.exists();

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            if (!fileExists) {
                bufferedWriter.write("Data, Ora, Actiune");
                bufferedWriter.newLine();
            }

            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            String line = currentDate + "," + currentTime + "," + action;

            // write
            bufferedWriter.write(line);
            bufferedWriter.newLine();

            // close
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Actiunea a fost salvata in fisierul csv");
        }
        catch (IOException e) {
            System.out.println("FAILED: csv" + e.getMessage());
        }
    }
}

