package com.seleniumrbm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsPage extends SitePage {
    private final By info = By.cssSelector(".product-information");
    private final By name = By.cssSelector(".product-information h2");
    private final By quantity = By.id("quantity");
    private final By addToCartButton = By.xpath("//button[contains(.,'Add to cart')]");

    public ProductDetailsPage(WebDriver driver) { super(driver); }

    /** Returns what is NOT displayed (empty list = everything is there). */
    public List<String> missingDetails() {
        String infoText = find(info).getText();
        List<String> missing = new ArrayList<>();
        if (find(name).getText().isBlank()) missing.add("name");
        for (String label : List.of("Category:", "Rs.", "Availability:", "Condition:", "Brand:")) {
            if (!infoText.contains(label)) missing.add(label);
        }
        return missing;
    }

    public void setQuantity(int value) {
        WebElement input = find(quantity);
        input.clear();
        input.sendKeys(String.valueOf(value));
    }

    public void addToCart() {
        click(addToCartButton);
        waitForCartModal();
    }
}
