package com.seleniumrbm.hooks;

import com.seleniumrbm.Context.ScenarioData;
import com.seleniumrbm.Context.TestContext;
import com.seleniumrbm.pages.AuthPage;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

/**
 * After hooks run from the HIGHEST order to the lowest:
 * 20 screenshot -> 10 delete the test user -> 1 close the browser.
 */
public class Hooks {
    private final TestContext ctx;
    private final ScenarioData data;

    public Hooks(TestContext ctx, ScenarioData data) {
        this.ctx = ctx;
        this.data = data;
    }

    @After(order = 20)
    public void screenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] png = ((TakesScreenshot) ctx.driver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(png, "image/png", "Failure screenshot");
        }
    }

    @After(value = "@cleanup", order = 10)
    public void deleteTestUser() {
        if (data.email == null) return;
        try {
            AuthPage auth = new AuthPage(ctx.driver());
            auth.open("/logout");
            auth.open();
            auth.login(data.email, data.account.get("password"));
            auth.open("/delete_account");
        } catch (Exception ignored) {
            // cleanup must never fail the scenario
        }
    }

    @After(order = 1)
    public void closeBrowser() {
        ctx.fermer();
    }
}
