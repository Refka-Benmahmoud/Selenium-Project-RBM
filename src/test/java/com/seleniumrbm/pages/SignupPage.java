package com.seleniumrbm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/** "ENTER ACCOUNT INFORMATION" form, then ACCOUNT CREATED! */
public class SignupPage extends SitePage {

    public SignupPage(WebDriver driver) { super(driver); }

    private By qa(String name) { return By.cssSelector("[data-qa='" + name + "']"); }

    public void fillAccountForm(Map<String, String> d) {
        click(By.id(d.get("title").equals("Mr") ? "id_gender1" : "id_gender2"));
        type(qa("password"), d.get("password"));
        select(qa("days"), d.get("day"));
        select(qa("months"), d.get("month"));
        select(qa("years"), d.get("year"));
        type(qa("first_name"), d.get("firstName"));
        type(qa("last_name"), d.get("lastName"));
        type(qa("address"), d.get("address"));
        select(qa("country"), d.get("country"));
        type(qa("state"), d.get("state"));
        type(qa("city"), d.get("city"));
        type(qa("zipcode"), d.get("zip"));
        type(qa("mobile_number"), d.get("mobile"));
    }

    public void createAccount() { click(qa("create-account")); }

    public void continueAfterCreation() {
        click(qa("continue-button"));
        // an ad can hijack the click: we are already logged in, so go home if the header is missing
        if (!isTextVisible("Logged in as")) {
            open("/");
        }}
}
