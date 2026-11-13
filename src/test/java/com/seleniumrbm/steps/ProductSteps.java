package com.seleniumrbm.steps;

import com.seleniumrbm.Context.TestContext;
import com.seleniumrbm.pages.ProductDetailsPage;
import com.seleniumrbm.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductSteps {
    private final TestContext ctx;

    public ProductSteps(TestContext ctx) { this.ctx = ctx; }

    @Given("I open the Products page")
    public void openProductsPage() {
        new ProductsPage(ctx.driver()).open();
    }

    @Then("the product list should not be empty")
    public void listNotEmpty() {
        assertTrue(new ProductsPage(ctx.driver()).productCount() > 0, "The product list is empty");
    }

    @When("I open the details of the first product")
    public void openFirstProduct() {
        new ProductsPage(ctx.driver()).openFirstProductDetails();
    }

    @Then("I should see the name, category, price, availability, condition and brand")
    public void detailsDisplayed() {
        List<String> missing = new ProductDetailsPage(ctx.driver()).missingDetails();
        assertTrue(missing.isEmpty(), "Missing on the details page: " + missing);
    }

    @When("I search for {string}")
    public void search(String word) {
        new ProductsPage(ctx.driver()).search(word);
    }

    @Then("every result should contain {string}")
    public void everyResultContains(String word) {
        List<String> names = new ProductsPage(ctx.driver()).resultNames();
        assertFalse(names.isEmpty(), "No result for " + word);
        for (String name : names) {
            assertTrue(name.toLowerCase().contains(word.toLowerCase()),
                    "'" + name + "' does not contain '" + word + "'");
        }
    }

    @When("I choose the category {string} then {string}")
    public void chooseCategory(String parent, String child) {
        new ProductsPage(ctx.driver()).chooseCategory(parent, child);
    }
}
