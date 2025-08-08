package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

        @AfterEach
        public void tearDown() {
            driver.quit();
            driver = null;
        }

        @Test
        void shouldTestSomething() {
            driver.get("http://localhost:9999/");
            WebElement form = driver.findElement(By.cssSelector("form"));
            form.findElement(By.cssSelector("[name='name']")).sendKeys("Воропаев Артемий");
            form.findElement(By.cssSelector("[name='phone']")).sendKeys("+79131038871");
            driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
            driver.findElement(By.cssSelector("[role='button']")).click();
            WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
            assertTrue(result.isDisplayed());
            assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                    result.getText().trim());

        }

    @Test
    void shouldTestEmptyNameField() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[name='name']")).sendKeys("");
        form.findElement(By.cssSelector("[name='phone']")).sendKeys("+79131038871");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());

    }

    @Test
    void shouldTestFailNameField() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[name='name']")).sendKeys("1");
        form.findElement(By.cssSelector("[name='phone']")).sendKeys("+79131038871");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                result.getText().trim());

    }

    @Test
    void shouldTestEmptyPhoneField() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[name='name']")).sendKeys("Воропаев Артемий");
        form.findElement(By.cssSelector("[name='phone']")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());

    }

    @Test
    void shouldTestFailPhoneField() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[name='name']")).sendKeys("Воропаев Артемий");
        form.findElement(By.cssSelector("[name='phone']")).sendKeys("9131038871");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                result.getText().trim());

    }

    @Test
    void shouldTestEmptyPhoneCheckBox() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[name='name']")).sendKeys("Воропаев Артемий");
        form.findElement(By.cssSelector("[name='phone']")).sendKeys("+79131038871");
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
        assertTrue(result.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных" +
                " и разрешаю сделать запрос в бюро кредитных историй", result.getText().trim());

    }
    }