package com.seleniumrbm.pages;

import com.seleniumrbm.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/** Helpers shared by all pages of automationexercise.com */
public abstract class SitePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected SitePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---------- navigation ----------
    public void open(String path) {
        driver.get(ConfigReader.get("base.url") + path);
        removeAds();
    }

    /** The site shows a cookie popup and Google ads that block clicks. */
    protected void removeAds() {
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('iframe, ins.adsbygoogle, .fc-consent-root').forEach(e => e.remove());"
                        + "document.body.style.overflow = 'auto';");
    }

    // ---------- basic actions ----------
    protected WebElement find(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected List<WebElement> findAll(By by) {
        return driver.findElements(by);
    }

    protected void click(By by) {
        removeAds();
        clickElement(wait.until(ExpectedConditions.elementToBeClickable(by)));
    }

    protected void clickElement(WebElement element) {
        try {
            element.click();
        } catch (ElementNotInteractableException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void type(By by, String value) {
        WebElement el = find(by);
        el.clear();
        el.sendKeys(value);
    }

    protected void select(By by, String visibleText) {
        new Select(find(by)).selectByVisibleText(visibleText);
    }

    protected void upload(By by, String absolutePath) {
        // file inputs are often not "visible": presence is enough
        wait.until(ExpectedConditions.presenceOfElementLocated(by)).sendKeys(absolutePath);
    }

    protected void scrollTo(By by) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    protected String text(By by) {
        return find(by).getText();
    }

    protected boolean isVisible(By by) {
        try {
            return find(by).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Waits until the text is displayed somewhere on the page.
     * Case-insensitive because the site writes titles in uppercase with CSS.
     */
    public boolean isTextVisible(String expected) {
        try {
            wait.until((ExpectedCondition<Boolean>) d ->
                    d.findElement(By.tagName("body")).getText().toLowerCase().contains(expected.toLowerCase()));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ---------- "Added!" pop-up (same on products and product details pages) ----------
    protected void waitForCartModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Your product has been added to cart')]")));
    }

    public void continueShopping() {
        click(By.xpath("//button[normalize-space()='Continue Shopping']"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Your product has been added to cart')]")));
    }

    public void viewCart() {
        click(By.xpath("//a[normalize-space()='View Cart']"));
    }
    public void openUrl(String fullUrl) {
        driver.get(fullUrl);
        removeAds();
    }
}
