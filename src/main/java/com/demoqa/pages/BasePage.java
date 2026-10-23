package com.demoqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage chứa WebDriver, WebDriverWait và các hàm tiện ích dùng chung
 * cho việc tương tác trên giao diện web theo mẫu thiết kế Page Object Model (POM).
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Chờ một phần tử hiển thị trên màn hình (Explicit Wait vị trí 1)
     */
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Chờ một phần tử sẵn sàng để click (Explicit Wait vị trí 2)
     */
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Cuộn trang đến phần tử cần tương tác
     */
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
    }

    /**
     * Click vào phần tử (có fallback sang JavaScript Click nếu bị che khuất)
     */
    public void click(By locator) {
        WebElement element = waitForClickable(locator);
        try {
            scrollToElement(element);
            element.click();
        } catch (Exception e) {
            clickViaJs(element);
        }
    }

    /**
     * Click thông qua JavascriptExecutor khi giao diện bị iframe/banner che
     */
    public void clickViaJs(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Gửi text vào ô nhập liệu
     */
    public void sendKeys(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        scrollToElement(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Lấy text hiển thị của một locator
     */
    public String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    /**
     * Xóa các banner/iframe quảng cáo của DemoQA để tránh ElementClickInterceptedException
     */
    public void removeAdsAndBanners() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "var fixedban = document.getElementById('fixedban'); if(fixedban) fixedban.remove();" +
                "var footer = document.querySelector('footer'); if(footer) footer.remove();" +
                "var iframes = document.querySelectorAll('iframe'); for(var i=0; i<iframes.length; i++) { iframes[i].remove(); }"
            );
        } catch (Exception ignored) {
        }
    }
}
