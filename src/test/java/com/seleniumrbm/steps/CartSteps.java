package com.seleniumrbm.steps;

import com.seleniumrbm.Context.ScenarioData;
import com.seleniumrbm.Context.TestContext;
import com.seleniumrbm.pages.CartPage;
import com.seleniumrbm.pages.ProductDetailsPage;
import com.seleniumrbm.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {
    private final TestContext ctx;
    private final ScenarioData data;

    public CartSteps(TestContext ctx, ScenarioData data) {
        this.ctx = ctx;
        this.data = data;
    }

    @When("I add product {int} and continue shopping")
    public void addAndContinue(int position) {
        ProductsPage products = new ProductsPage(ctx.driver());
        data.addedProducts.add(products.addToCart(position));
        products.continueShopping();
    }

    @When("I add product {int} and view the cart")
    public void addAndViewCart(int position) {
        ProductsPage products = new ProductsPage(ctx.driver());
        data.addedProducts.add(products.addToCart(position));
        products.viewCart();
    }

    @When("I set the quantity to {int} and add to cart")
    public void setQuantityAndAdd(int quantity) {
        ProductDetailsPage details = new ProductDetailsPage(ctx.driver());
        details.setQuantity(quantity);
        details.addToCart();
    }

    @When("I view the cart")
    public void viewCart() {
        new ProductDetailsPage(ctx.driver()).viewCart();
    }

    @When("I remove the first product from the cart")
    public void removeFirst() {
        new CartPage(ctx.driver()).removeFirstProduct();
    }

    @Then("the cart should contain {int} products")
    public void cartContains(int expected) {
        CartPage cart = new CartPage(ctx.driver());
        assertTrue(cart.waitForRowCount(expected),
                "Expected " + expected + " products in the cart but found " + cart.rowCount());
    }

    @Then("the cart should list the added products")
    public void cartListsAddedProducts() {
        CartPage cart = new CartPage(ctx.driver());
        cart.waitForRowCount(data.addedProducts.size());
        for (int i = 0; i < cart.rowCount(); i++) {
            assertTrue(data.addedProducts.contains(cart.productName(i)),
                    "Unexpected product in the cart: " + cart.productName(i));
        }
    }

    @Then("each cart row should have price multiplied by quantity equal to total")
    public void priceTimesQuantity() {
        CartPage cart = new CartPage(ctx.driver());
        for (int i = 0; i < cart.rowCount(); i++) {
            assertEquals(cart.price(i) * cart.quantity(i), cart.total(i),
                    "Wrong total on row " + (i + 1) + " (" + cart.productName(i) + ")");
        }
    }

    @Then("the quantity in the cart should be {int}")
    public void quantityInCart(int expected) {
        CartPage cart = new CartPage(ctx.driver());
        assertTrue(cart.waitForRowCount(1), "The cart should contain one product");
        assertEquals(expected, cart.quantity(0));
    }

    @Then("the product should disappear from the cart")
    public void productDisappears() {
        assertTrue(new CartPage(ctx.driver()).waitForRowCount(0), "The product is still in the cart");
    }
}
