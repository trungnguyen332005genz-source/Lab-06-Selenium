package com.demoqa.tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BaseTest thiết lập và giải phóng WebDriver,
 * đồng thời cung cấp phương thức captureScreen theo mẫu yêu cầu trong PDF.
 */
public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        // Tự động kiểm tra vị trí file chrome.exe trên máy người dùng nếu cần
        String userHome = System.getProperty("user.home");
        File localChrome = new File(userHome, "AppData/Local/Google/Chrome/Application/chrome.exe");
        if (localChrome.exists()) {
            options.setBinary(localChrome);
        }

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-popup-blocking");

        // Hỗ trợ chế độ headless qua tham số dòng lệnh nếu cần: mvn test -Dheadless=true
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    }

    /**
     * MẪU CODE CHỤP MÀN HÌNH THEO ĐÚNG ĐẶC TẢ TRONG PDF (Trang 3)
     */
    public void captureScreen(String prefix) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File dest = new File("screenshots/" + prefix + "_" + time + ".png");
        if (dest.getParentFile() != null && !dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        FileUtils.copyFile(src, dest);
        System.out.println("[Screenshot] Đã chụp ảnh màn hình lưu tại: " + dest.getAbsolutePath());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Chụp màn hình khi test lỗi (theo yêu cầu PDF)
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                captureScreen("FAIL_" + result.getName());
            } catch (IOException e) {
                System.err.println("Lỗi khi chụp màn hình thất bại: " + e.getMessage());
            }
        }

        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
        }
    }
}
