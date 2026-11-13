package com.seleniumrbm.steps;

import com.seleniumrbm.Context.TestContext;
import com.seleniumrbm.pages.ContactPage;
import com.seleniumrbm.pages.HomePage;
import com.seleniumrbm.utils.TestData;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FormSteps {
    private final TestContext ctx;

    public FormSteps(TestContext ctx) { this.ctx = ctx; }

    @When("I open the Contact us page")
    public void openContact() {
        new ContactPage(ctx.driver()).open();
    }

    @When("I fill the contact form")
    public void fillContact() {
        new ContactPage(ctx.driver()).fill("Student", TestData.uniqueEmail(), "Question", "Hello, this is a test message.");
    }

    @When("I attach a small file")
    public void attachFile() {
        try {
            Path file = Files.createTempFile("upload-", ".txt");
            Files.writeString(file, "small test file");
            new ContactPage(ctx.driver()).attachFile(file.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @When("I submit the form and accept the alert")
    public void submitAndAccept() {
        new ContactPage(ctx.driver()).submitAndAcceptAlert();
    }

    @When("I subscribe to the newsletter with a new email")
    public void subscribe() {
        new HomePage(ctx.driver()).subscribe(TestData.uniqueEmail());
    }
}
