package com.seleniumrbm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends SitePage {
    private final By logoutLink = By.cssSelector("a[href='/logout']");
    private final By deleteLink = By.cssSelector("a[href='/delete_account']");
    private final By subscribeEmail = By.id("susbscribe_email"); // the typo is on the real site
    private final By subscribeButton = By.id("subscribe");

    public HomePage(WebDriver driver) { super(driver); }

    public void open() { open("/"); }

    public void logout() { click(logoutLink); }

    public void deleteAccount() { click(deleteLink); }

    /** The subscription box is in the footer of every page. */
    public void subscribe(String email) {
        scrollTo(subscribeEmail);
        type(subscribeEmail, email);
        click(subscribeButton);
    }
}
