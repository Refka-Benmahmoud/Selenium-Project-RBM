package com.seleniumrbm.pages;

import com.seleniumrbm.utils.TestData;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CartPage extends SitePage {
    private final By rows = By.cssSelector("tr[id^='product-']");

    public CartPage(WebDriver driver) { super(driver); }

    public int rowCount() { return findAll(rows).size(); }

    /** Waits until the cart has exactly this number of rows. */
    public boolean waitForRowCount(int expected) {
        try {
            wait.until((ExpectedCondition<Boolean>) d -> d.findElements(rows).size() == expected);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private String cell(int row, String css) {
        return findAll(rows).get(row).findElement(By.cssSelector(css)).getText();
    }

    public String productName(int row) { return cell(row, ".cart_description h4 a"); }

    public int price(int row) { return TestData.toNumber(cell(row, ".cart_price p")); }

    public int quantity(int row) { return TestData.toNumber(cell(row, ".cart_quantity button")); }

    public int total(int row) { return TestData.toNumber(cell(row, ".cart_total_price")); }

    public void removeFirstProduct() { click(By.cssSelector("a.cart_quantity_delete")); }

    public void proceedToCheckout() { click(By.cssSelector("a.check_out")); }
}
