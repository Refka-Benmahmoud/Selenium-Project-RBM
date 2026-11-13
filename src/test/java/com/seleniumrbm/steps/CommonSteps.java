package com.seleniumrbm.steps;

import com.seleniumrbm.Context.TestContext;
import com.seleniumrbm.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSteps {
    private final TestContext ctx;

    public CommonSteps(TestContext ctx) { this.ctx = ctx; }

    @Given("I am on the home page")
    public void onHomePage() {
        new HomePage(ctx.driver()).open();
    }

    /** Used by almost every scenario: titles, success and error messages. */
    @Then("I should see {string}")
    public void shouldSee(String text) {
        assertTrue(new HomePage(ctx.driver()).isTextVisible(text), "Text not found on the page: " + text);
    }
}
