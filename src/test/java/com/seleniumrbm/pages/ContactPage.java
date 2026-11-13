package com.seleniumrbm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactPage extends SitePage {

    public ContactPage(WebDriver driver) { super(driver); }

    public void open() { open("/contact_us"); }

    public void fill(String name, String email, String subject, String message) {
        type(By.cssSelector("[data-qa='name']"), name);
        type(By.cssSelector("[data-qa='email']"), email);
        type(By.cssSelector("[data-qa='subject']"), subject);
        type(By.cssSelector("[data-qa='message']"), message);
    }

    /** sendKeys on the file input, do NOT click the button. */
    public void attachFile(String absolutePath) {
        upload(By.cssSelector("input[name='upload_file']"), absolutePath);
    }

    public void submitAndAcceptAlert() {
        click(By.cssSelector("[data-qa='submit-button']"));
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }
}
