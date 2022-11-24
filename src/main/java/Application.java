import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        WhatsAppClient whatsAppClient = new WhatsAppClient();
        Scanner scanner = new Scanner(System.in);

        // Получение данных из файлов text и contacts
        List<String> clients = whatsAppClient.readFileForContacts("src/main/resources/contacts");

        // Создание драйвера для chrome и переход в приложение whatsapp web
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://web.whatsapp.com/");

        // Ожидание аутентификации с помощью QR кода
        System.out.println("\nПосле аутентификации и загрузки страницы whatsapp web нажми клавишу \"Enter\", чтобы приложение начало работать.");
        scanner.nextLine();

        int time = 0;

        for (String client: clients) {
            try {
                // Строим URI из текста для сообщения и номера клиента и переходим по нему
                driver.get(whatsAppClient.buildURI("", client));
                waitingFor(15515 + time, 19578 + time);

                if (driver.findElements(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p")).size() > 0) {
                    // Ищем элемент со вставленным сообщением и кликаем на него
                    driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p")).click();
                    waitingFor(1567, 3124);

                    // Вставляем текст из буфера обмена
                    driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p")).sendKeys( Keys.CONTROL, "v");

                    waitingFor(1564, 2435);

                    // Ищем кнопку для отправки сообщения и кликаем на неё
                    driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]/button/span")).click();
                    System.out.println("Сообщение успешно отослано контакту " + client + ".");
                    waitingFor(6554 + time, 7865 + time);
                } else {
                    System.out.println("Контакт " + client + " не найден в whatsapp.");
                }
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