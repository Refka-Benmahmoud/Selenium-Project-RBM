package com.seleniumrbm.steps;

import com.seleniumrbm.Context.ScenarioData;
import com.seleniumrbm.Context.TestContext;
import com.seleniumrbm.pages.CartPage;
import com.seleniumrbm.pages.CheckoutPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSteps {
    private final TestContext ctx;
    private final ScenarioData data;

    public CheckoutSteps(TestContext ctx, ScenarioData data) {
        this.ctx = ctx;
        this.data = data;
    }

    @When("I proceed to checkout")
    public void proceedToCheckout() {
        new CartPage(ctx.driver()).proceedToCheckout();
    }

    @Then("the delivery address should match my account")
    public void addressMatches() {
        List<String> missing = new CheckoutPage(ctx.driver()).missingInAddress(data.account);
        assertTrue(missing.isEmpty(), "Not found in the delivery address: " + missing);
    }

    @Then("the order review should list the added products")
    public void reviewListsProducts() {
        List<String> reviewed = new CheckoutPage(ctx.driver()).reviewedProductNames();
        assertTrue(reviewed.containsAll(data.addedProducts),
                "Review shows " + reviewed + " but expected " + data.addedProducts);
    }

    @When("I place the order with comment {string} and these card details:")
    public void placeOrder(String comment, DataTable card) {
        CheckoutPage checkout = new CheckoutPage(ctx.driver());
        checkout.typeComment(comment);
        checkout.clickPlaceOrder();
        checkout.pay(card.asMaps().get(0));
    }
}
