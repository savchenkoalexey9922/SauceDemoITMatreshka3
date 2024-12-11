import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AuthorizationTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        //options.addArguments("incognito");
        //    options.addArguments("headless"); // прогон тестов в скрытом режиме браузера
        // tions.addArguments("disable-notification"); // не показывать уведомления
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //неявное ожидание прогрузки элементов на странице
    }

    @Test   //positive
    public void ckeckInputs() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("standard_user"); // Прописываем значение логина
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("secret_sauce"); //Прописываем значение пароля
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click(); // Жмакаем Login
        boolean elementOnPage = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[1]/div[2]/div")).isDisplayed(); //Проверка перехода на страницу каталога товаров
        Assert.assertTrue(elementOnPage);
    }

    @Test   //no password
    public void ckeckNoPassword() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("standard_user"); // Прописываем значение логина
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click(); // Жмакаем Login
        boolean elementErrorPassOnPage = driver.findElement(By.xpath("//*[contains(text(), 'Epic sadface: Password is required')]")).isDisplayed(); //Проверка вывода ошибки "Password is required"
        Assert.assertTrue(elementErrorPassOnPage);
    }

    @Test   //no login
    public void ckeckNoLogin() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("secret_sauce"); // Прописываем значение пароля
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click(); // Жмакаем Login
        boolean elementErrorLoginOnPage = driver.findElement(By.xpath("//*[contains(text(), 'Epic sadface: Username is required')]")).isDisplayed(); //Проверка вывода ошибки "Username is required"
        Assert.assertTrue(elementErrorLoginOnPage);
    }

    @Test   //no valid user
    public void ckeckNoValidUser() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("novalid_user"); // Прописываем значение novalid логина
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("secret_sauce"); //Прописываем значение пароля
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click(); // Жмакаем Login
        boolean elementErrorLoginOnPage = driver.findElement(By.xpath("//*[contains(text(), 'Epic sadface: Username and password do not match any user in this service')]")).isDisplayed(); //Проверка вывода ошибки "Username is required"
        Assert.assertTrue(elementErrorLoginOnPage);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }


}
