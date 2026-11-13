package com.seleniumrbm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutPage extends SitePage {
    private final By addressDelivery = By.id("address_delivery");
    private final By productNames = By.cssSelector(".cart_description h4 a");
    private final By comment = By.cssSelector("textarea[name='message']");
    private final By placeOrder = By.cssSelector("a[href='/payment']");

    public CheckoutPage(WebDriver driver) { super(driver); }

    /** Returns the account values that are NOT in the delivery address (empty = all good). */
    public List<String> missingInAddress(Map<String, String> account) {
        String address = find(addressDelivery).getText();
        List<String> missing = new ArrayList<>();
        for (String key : List.of("firstName", "lastName", "address", "city", "state", "zip", "country", "mobile")) {
            if (!address.contains(account.get(key))) missing.add(key + "=" + account.get(key));
        }
        return missing;
    }

    public List<String> reviewedProductNames() {
        find(productNames);
        return findAll(productNames).stream().map(WebElement::getText).toList();
    }

    public void typeComment(String text) { type(comment, text); }

    public void clickPlaceOrder() { click(placeOrder); }

    /** Card keys: name, number, cvc, month, year */
    public void pay(Map<String, String> card) {
        type(By.cssSelector("[data-qa='name-on-card']"), card.get("name"));
        type(By.cssSelector("[data-qa='card-number']"), card.get("number"));
        type(By.cssSelector("[data-qa='cvc']"), card.get("cvc"));
        type(By.cssSelector("[data-qa='expiry-month']"), card.get("month"));
        type(By.cssSelector("[data-qa='expiry-year']"), card.get("year"));
        click(By.cssSelector("[data-qa='pay-button']"));
    }
}
