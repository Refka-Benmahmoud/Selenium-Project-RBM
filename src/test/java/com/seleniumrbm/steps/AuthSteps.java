package com.seleniumrbm.steps;

import com.seleniumrbm.Context.ScenarioData;
import com.seleniumrbm.Context.TestContext;
import com.seleniumrbm.pages.AuthPage;
import com.seleniumrbm.pages.HomePage;
import com.seleniumrbm.pages.SignupPage;
import com.seleniumrbm.utils.TestData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthSteps {
    private final TestContext ctx;
    private final ScenarioData data;

    public AuthSteps(TestContext ctx, ScenarioData data) {
        this.ctx = ctx;
        this.data = data;
    }

    // ---------- preconditions ----------
    @Given("I am on the login page")
    public void onLoginPage() {
        new AuthPage(ctx.driver()).open();
    }

    /** Creates a fresh account through the UI, then logs out. */
    @Given("a registered user exists")
    public void registeredUserExists() {
        data.email = TestData.uniqueEmail();
        AuthPage auth = new AuthPage(ctx.driver());
        auth.open();
        auth.startSignup(data.name, data.email);
        SignupPage signup = new SignupPage(ctx.driver());
        signup.fillAccountForm(data.account);
        signup.createAccount();
        signup.continueAfterCreation();
        new HomePage(ctx.driver()).logout();
    }

    // ---------- signup ----------
    @When("I start signing up with a new name and email")
    public void startSignupNew() {
        data.email = TestData.uniqueEmail();
        new AuthPage(ctx.driver()).startSignup(data.name, data.email);
    }

    @When("I start signing up with the existing email")
    public void startSignupExisting() {
        new AuthPage(ctx.driver()).startSignup(data.name, data.email);
    }

    @When("I fill the account form with:")
    public void fillAccountForm(DataTable table) {
        data.account = new HashMap<>(table.asMaps().get(0));
        new SignupPage(ctx.driver()).fillAccountForm(data.account);
    }

    @When("I click Create Account")
    public void createAccount() {
        new SignupPage(ctx.driver()).createAccount();
    }

    @When("I click Continue")
    public void clickContinue() {
        new SignupPage(ctx.driver()).continueAfterCreation();
    }

    @When("I delete my account")
    public void deleteAccount() {
        new HomePage(ctx.driver()).deleteAccount();
    }

    // ---------- login / logout ----------
    @When("I log in with email {string} and password {string}")
    public void logInWith(String email, String password) {
        new AuthPage(ctx.driver()).login(email, password);
    }

    @When("I log in with my account")
    public void logInWithMyAccount() {
        new AuthPage(ctx.driver()).login(data.email, data.account.get("password"));
    }

    @When("I click Logout")
    public void logout() {
        new HomePage(ctx.driver()).logout();
    }

    @Then("I should be logged in as my user")
    public void loggedInAsMyUser() {
        String expected = "Logged in as " + data.name;
        assertTrue(new HomePage(ctx.driver()).isTextVisible(expected), "Header should show: " + expected);
    }

    @Then("I should see the login form")
    public void loginFormVisible() {
        assertTrue(new AuthPage(ctx.driver()).isLoginFormVisible(), "'Login to your account' form not visible");
    }
}
