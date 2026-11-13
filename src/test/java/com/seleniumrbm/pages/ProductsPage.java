package com.seleniumrbm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductsPage extends SitePage {
    private final By cards = By.cssSelector(".features_items .product-image-wrapper");
    private final By names = By.cssSelector(".features_items .productinfo p");
    private final By firstViewProduct = By.cssSelector(".features_items .choose a");
    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");

    public ProductsPage(WebDriver driver) { super(driver); }

    public void open() { open("/products"); }

    public int productCount() {
        wait.until(ExpectedConditions.presenceOfElementLocated(cards));
        return findAll(cards).size();
    }

    public void openFirstProductDetails() { click(firstViewProduct); }

    public void search(String word) {
        type(searchInput, word);
        click(searchButton);
        isTextVisible("SEARCHED PRODUCTS"); // wait for the results page to load
    }

    public List<String> resultNames() {
        return findAll(names).stream().map(WebElement::getText).toList();
    }

    /** Opens a category from the left sidebar, e.g. ("Women", "Dress"). */
    public void chooseCategory(String parent, String child) {
        click(By.cssSelector("a[href='#" + parent + "']"));
        click(By.xpath("//div[@id='" + parent + "']//a[normalize-space()='" + child + "']"));
    }

    /** Adds the product at this position (1 = first) and returns its name. */
    public String addToCart(int position) {
        wait.until(ExpectedConditions.presenceOfElementLocated(cards));
        removeAds();
        WebElement card = findAll(cards).get(position - 1);
        String name = card.findElement(By.cssSelector(".productinfo p")).getText();
        // "Add to cart" is inside an overlay that only appears on hover
        new Actions(driver).moveToElement(card).perform();
        WebElement add = card.findElement(By.cssSelector(".overlay-content .add-to-cart"));
        wait.until(ExpectedConditions.elementToBeClickable(add));
        clickElement(add);
        waitForCartModal();
        return name;
    }
}
