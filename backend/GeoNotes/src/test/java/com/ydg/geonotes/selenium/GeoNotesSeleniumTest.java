package com.ydg.geonotes.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GeoNotesSeleniumTest {

    private WebDriver driver;
    // Base URL artık API yerine direkt static klasöründeki HTML sayfalarına bakıyor
    private final String baseUrl = "http://localhost:8083/";

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Jenkins ortamı için zorunlu
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // Chrome'un arayüzsüz modda daha stabil çalışması için pencere boyutu sabitleme
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    @DisplayName("Senaryo 1: Ana Sayfa ve Liste Görünümü Kontrolü")
    void testSystemIsUpAndEmpty() {
        // index.html sayfasına gidiyoruz
        driver.get(baseUrl + "index.html");
        String bodyText = driver.getPageSource();

        // HTML içindeki json-preview div'indeki [ ] karakterlerini kontrol ediyoruz
        assertTrue(bodyText.contains("[") && bodyText.contains("]"),
                "Ana sayfa yüklenemedi veya JSON önizlemesi bulunamadı!");
    }

    @Test
    @Order(2)
    @DisplayName("Senaryo 2: Not Ekleme Sayfası Erişilebilirliği")
    void testDataExistsAfterAdd() {
        // add-note.html sayfasına gidiyoruz
        driver.get(baseUrl + "add-note.html");
        String bodyText = driver.getPageSource();

        // Sayfa içeriğinde beklediğimiz bir başlığı kontrol ediyoruz
        assertTrue(bodyText.contains("Yeni Not Oluştur"),
                "Not ekleme sayfası yüklenemedi!");
    }

    @Test
    @Order(3)
    @DisplayName("Senaryo 3: Güncelleme Sayfası Doğrulaması")
    void testUpdateEndpointAccessibility() {
        // update-note.html sayfasına gidiyoruz
        driver.get(baseUrl + "update-note.html");
        String bodyText = driver.getPageSource();

        // Sayfa içeriğinde 404 hatası olmadığını ve güncelleme başlığını kontrol ediyoruz
        assertTrue(!bodyText.contains("Not Found") && bodyText.contains("Notu Düzenle"),
                "Güncelleme sayfası bulunamadı veya hatalı!");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
  }