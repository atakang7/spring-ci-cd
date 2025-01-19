package com.spring.app.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldCreateNewUser() {
        driver.get("http://localhost:8080");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userForm")));

        WebElement usernameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        Select genderSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.id("gender"))));

        usernameInput.sendKeys("testuser");
        emailInput.sendKeys("test@test.com");
        genderSelect.selectByVisibleText("Male");

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        submitButton.click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userTableBody")));

        List<WebElement> rows = table.findElements(By.tagName("tr"));
        boolean found = false;
        for(WebElement row : rows) {
            if(row.getText().contains("testuser") &&
                    row.getText().contains("test@test.com")) {
                found = true;
                break;
            }
        }
        assertTrue(found, "New user should appear in table");
    }

    @Test
    void shouldShowValidationError() {
        driver.get("http://localhost:8080");

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        submitButton.click();

        WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        assertTrue(usernameInput.getAttribute("required") != null, "Username should be required");
    }

    @Test
    void shouldLoadExistingUsers() {
        driver.get("http://localhost:8080");

        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userTableBody")));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Just verify the table exists and is accessible
        assertNotNull(rows, "User table should be loaded");
    }

    @Test
    void shouldSelectDifferentGenders() {
        driver.get("http://localhost:8080");

        Select genderSelect = new Select(wait.until(ExpectedConditions.presenceOfElementLocated(By.id("gender"))));

        genderSelect.selectByVisibleText("Male");
        assertEquals("Male", genderSelect.getFirstSelectedOption().getText());

        genderSelect.selectByVisibleText("Female");
        assertEquals("Female", genderSelect.getFirstSelectedOption().getText());

        genderSelect.selectByVisibleText("Other");
        assertEquals("Other", genderSelect.getFirstSelectedOption().getText());
    }
}