package vpn;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VPNLogManager {

    private static final String LOG_file = " vpn_logs.text";

    public static void log(String message )
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_file,true)))
        {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            writer.write(" [ "+timestamp+ " ] " + message);
            writer.newLine();
        }
        catch(IOException e)
        {
            System.err.println("Error writing log : "+e.getMessage());
        }
    }
}
