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
        waitingFor(49485, 53321);

        for (String client: clients) {
            // log to console
            System.out.println("Отправка сообщения на номер: " + client);
            System.out.println("С сообщением: \n" + textMessage);

            try {
                // Строим URI из текста для сообщения и номера клиента и переходим по нему
                driver.get(whatsAppClient.buildURI("", client));
                waitingFor(25485, 30321);

                // Ищем элемент со вставленным сообщением и кликаем на него
                driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2]")).click();
                waitingFor(3230, 5345);

                // Вставляем текст
                driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2]")).sendKeys(textMessage);
                waitingFor(3320, 5411);

                // Ищем кнопку для отправки сообщения и кликаем на неё
                driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]/button/span")).click();
                waitingFor(12235, 15151);
            } catch (UnsupportedOperationException e) {;
                e.printStackTrace();
            }
        }

        driver.quit();

    }

    public static void waitingFor(int min, int max) {
        max -= min;

        try {
            Thread.sleep((int) (Math.random() * ++max) + min);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}