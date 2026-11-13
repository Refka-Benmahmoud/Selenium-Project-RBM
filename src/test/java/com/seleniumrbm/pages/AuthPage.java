package com.seleniumrbm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** The /login page: login form + "New User Signup!" form. */
public class AuthPage extends SitePage {
    private final By loginEmail = By.cssSelector("[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("[data-qa='login-password']");
    private final By loginButton = By.cssSelector("[data-qa='login-button']");
    private final By signupName = By.cssSelector("[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("[data-qa='signup-button']");
    private final By loginTitle = By.xpath("//h2[normalize-space()='Login to your account']");

    public AuthPage(WebDriver driver) { super(driver); }

    public void open() { open("/login"); }

    public void login(String email, String password) {
        type(loginEmail, email);
        type(loginPassword, password);
        click(loginButton);
    }

    public void startSignup(String name, String email) {
        type(signupName, name);
        type(signupEmail, email);
        click(signupButton);
    }

    public boolean isLoginFormVisible() { return isVisible(loginTitle); }
}
