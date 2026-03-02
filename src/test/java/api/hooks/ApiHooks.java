package api.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import api.stepdefinitions.ApiStepdefs;

public class ApiHooks {

    @Before
    public void beforeAllTests() {
        System.out.println("=== Starting API scenario ===");
    }

    @After
    public void afterAllTests(Scenario scenario) {
        System.out.println("=== Ending API scenario ===");
        if (scenario.isFailed()) {
            System.out.println("=== Scenario FAILED: " + scenario.getName() + " ===");
            Response lastResponse = ApiStepdefs.getResponse();
            if (lastResponse != null) {
                System.out.println("=== Response Body ===");
                System.out.println(lastResponse.getBody().asString());
                System.out.println("====================");
            } else {
                System.out.println("No response captured for this scenario.");
            }
        } else {
            System.out.println("=== Scenario PASSED: " + scenario.getName() + " ===");
        }
    }
}
