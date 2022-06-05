import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WhatsAppClient {
    public String buildURI(String text, String phoneNumber) {
        return "https://web.whatsapp.com/send?phone=" + phoneNumber + "&text=" + text + "&app_absent=0";
    }

    public String readFileForMessage(String path, boolean isForURI) {
        StringBuilder fileContent = new StringBuilder();

        String linefeed = "\n";
        if (isForURI) {
            linefeed = "%0a";
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            String sub;
            fileContent.append(bufferedReader.readLine());
            while ((sub = bufferedReader.readLine()) != null) {
                fileContent.append(linefeed);
                fileContent.append(sub);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent.toString()
                .replace(" ", "%20")
                .replace("️", "%20%20")
                .replace("⠀", "%20")
                .replace("!", "%21")
                .replace("\"", "%22");
    }

    public List<String> readFileForContacts(String path) {
        List<String> numbers = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            String sub;
            while ((sub = bufferedReader.readLine()) != null) {
                numbers.add(sub);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numbers;
    }
}