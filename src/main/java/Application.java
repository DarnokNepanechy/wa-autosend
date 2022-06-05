import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        WhatsAppClient whatsAppClient = new WhatsAppClient();

        // Получение данных из файлов text и contacts
        List<String> clients = whatsAppClient.readFileForContacts("src/main/resources/contacts");
        String textMessage = whatsAppClient.readFileForMessage("src/main/resources/text", false);

        // Создание драйвера для chrome и переход в приложение whatsapp web
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://web.whatsapp.com/");

        // Ожидание аутентификации с помощью QR кода
        try {
            Thread.sleep(50_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (String client: clients) {
            // Строим URI из текста для сообщения и номера клиента и переходим по нему
            driver.get(whatsAppClient.buildURI("", client));
            try {
                Thread.sleep(randomInt(25485, 30321));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Ищем элемент со вставленным сообщением и кликаем на него
            driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2]")).click();
            try {
                Thread.sleep(randomInt(1230, 2345));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Вставляем текст
            driver.findElement(By.className("p3_M1")).sendKeys(textMessage);
            try {
                Thread.sleep(randomInt(2320, 3411));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Ищем кнопку для отправки сообщения и кликаем на неё
            driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]/button/span")).click();
            try {
                Thread.sleep(randomInt(12235, 15151));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        driver.quit();
    }

    public static int randomInt(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
