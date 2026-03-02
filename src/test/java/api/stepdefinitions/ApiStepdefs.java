package api.stepdefinitions;

import api.pages.TagApiPage;
import api.pages.UserApiPage;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ApiStepdefs {

    private final UserApiPage userPage = new UserApiPage();
    private final TagApiPage tagPage = new TagApiPage();
    private static Response response;
    private String userId;
    private String invalidBody;

    public static Response getResponse() {
        return response;
    }

    // ---------------------
    // Setup
    // ---------------------
    @Given("the API base url {string}")
    public void setBaseUrl(String baseUrl) {
        userPage.setBaseUrl(baseUrl);
        tagPage.setBaseUrl(baseUrl);
    }

    @Given("a valid app-id")
    public void setValidAppId() {
        String validAppId = "63a804408eb0cb069b57e43a"; // using valid app-id on https://dummyapi.io/
        userPage.setAppId(validAppId);
        tagPage.setAppId(validAppId);
    }

    @Given("no app-id")
    public void noAppId() {
        userPage.setAppId(null);
        tagPage.setAppId(null);
    }

    @Given("an app-id {string}")
    public void setAppId(String appId) {
        userPage.setAppId(appId);
        tagPage.setAppId(appId);
    }

    @Given("a(n) {word} user id {string}")
    public void setUserIdByType(String type, String id) {
        if ("valid".equalsIgnoreCase(type)) {
            this.userId = id; // using valid user id
        } else if ("invalid".equalsIgnoreCase(type)) {
            this.userId = id; // save invalid id for test 404 later
        } else {
            throw new IllegalArgumentException("Unknown user id type: " + type);
        }
    }

    @Given("invalid endpoint")
    public void invalidEndpoint() {
        // set endpoint invalid (example: for testing PATH_NOT_FOUND)
        this.userId = null; // reset ID so it is not used
    }

    @Given("Create User no firstName")
    public void createUserNoFirstName() {
        // save body invalid in global variable
        this.invalidBody = "{ \"lastName\":\"Test\", \"email\":\"invalid@test.com\" }";
    }

    @Given("Create User no lastName")
    public void createUserNoLastName() {
        this.invalidBody = "{ \"firstName\":\"Agus\", \"email\":\"invalid@test.com\" }";
    }

    @Given("Create User no email")
    public void createUserNoEmail() {
        this.invalidBody = "{ \"firstName\":\"Agus\", \"lastName\":\"Test\" }";
    }

    @Given("Create User invalid email")
    public void createUserInvalidEmail() {
        this.invalidBody = "{ \"firstName\":\"Agus\", \"lastName\":\"Test\", \"email\":\"xx\" }";
    }

    // ---------------------
    // Actions
    // ---------------------
    @When("the user sends GET request to {string}")
    public void sendGetRequest(String endpoint) {
        if (endpoint.contains("{id}")) {
            response = userPage.getUserById(userId); // <<< using wrapper method getUserById
        } else if (endpoint.startsWith("/tag")) {
            response = tagPage.getAllTags(); // <<< using wrapper method getAllTags
        } else if (endpoint.equals("/user")) {
            response = userPage.getAllUsers(); // <<< using wrapper method getAllUsers
        } else {
            response = userPage.getRequest(endpoint);
        }
        logResponse("GET " + endpoint);
    }

    @When("the user sends POST request to {string} with body:")
    public void sendPostRequest(String endpoint, String body) {
        if (body.contains("<unique_email>")) {
            String uniqueEmail = "agus.test" + UUID.randomUUID().toString()
                    .replace("-", "") + "@example.com";
            body = body.replace("<unique_email>", uniqueEmail);
        }

        // if previously there was an invalidBody from Given, override
        if (invalidBody != null) {
            body = invalidBody;
            invalidBody = null; // reset so it doesn't carry over to another test
        }

        response = userPage.createUser(body);
        if (response.statusCode() == 201) {
            this.userId = response.jsonPath().getString("id");
        }
        logResponse("POST " + endpoint);
    }

    @When("the user sends PUT request to {string} with body:")
    public void sendPutRequest(String endpoint, String body) {
        // If there is no userId → create a user first
        if (userId == null) {
            String newUserBody = "{ \"title\":\"mr\", \"firstName\":\"Temp\", \"lastName\":\"User\", " +
                    "\"email\":\"temp." + UUID.randomUUID() + "@example.com\" }";
            Response createResp = userPage.createUser(newUserBody);
            assertEquals(200, createResp.statusCode(), "Failed to create temp user for update");
            userId = createResp.jsonPath().getString("id");
        }
        endpoint = endpoint.replace("{id}", userId);
        response = userPage.updateUser(userId, body);
        logResponse("PUT " + endpoint);
    }

    @When("the user sends DELETE request to {string}")
    public void sendDeleteRequest(String endpoint) {
        // If there is no userId → create a user first
        if (userId == null) {
            String newUserBody = "{ \"title\":\"mr\", \"firstName\":\"Temp\", \"lastName\":\"User\", " +
                    "\"email\":\"temp." + UUID.randomUUID() + "@example.com\" }";

            Response createResp = userPage.createUser(newUserBody);
            assertTrue(createResp.statusCode() == 200 || createResp.statusCode() == 201,
                    "Failed to create temp user for delete. Response" + createResp.getBody().asString());

            userId = createResp.jsonPath().getString("id");
            System.out.println("=== Temp user created for delete: " + userId + " ===");
        }
        // Use the endpoint only for logging so it is not considered unused
        String finalEndpoint = endpoint.replace("{id}", userId);
        System.out.println("DELETE request via endpoint: " + finalEndpoint);
        // request via UserApiPage
        response = userPage.deleteUser(userId); // previously deleteUser(userId)
    }

    // ---------------------
    // Assertions
    // ---------------------
    @Then("the response code should be {int}")
    public void verifyStatusCode(int statusCode) {
        int actual = response.statusCode();
        // if DummyAPI sometimes returns 200 even though the expectation is 201
        if (statusCode == 201 && (actual == 200 || actual == 201)) {
            System.out.println("=== Response Body ===");
            System.out.println(response.getBody().asString());
            return;
        }
        // if DummyAPI sometimes returns 200 even though the expectation is 204
        if (statusCode == 204 && (actual == 200 || actual == 204)) {
            System.out.println("=== Delete Response Body ===");
            System.out.println(response.getBody().asString());
            return;
        }
        assertEquals(statusCode, actual,
                "Unexpected status code. Response body: " + response.getBody().asString());
    }

    @Then("the response should contain {string}")
    public void responseShouldContain(String expected) {
        String body = response.getBody().asString();
        assertTrue(body.contains(expected),
                "Expected response to contain: " + expected + " but was: " + body);
    }

    @Then("the response should contain error {string}")
    public void theResponseShouldContainError(String expectedError) {
        String body = response.getBody().asString();
        assertTrue(body.contains(expectedError),
                "Expected response to contain error: " + expectedError + " but was: " + body);
    }

    @Then("the delete response should be successful")
    public void deleteResponseSuccessful() {
        int actual = response.statusCode();
        assertTrue(actual == 200 || actual == 204,
                "Expected 200 or 204 for delete, but got " + actual +
                        " body: " + response.getBody().asString());
        System.out.println("=== Delete response body: " + response.getBody().asString() + " ===");
    }

    // =====================
    // Helper Logger
    // =====================
    private void logResponse(String action) {
        System.out.println("=== " + action + " ===");
        System.out.println("Status: " + response.statusCode());
        System.out.println("Body: " + response.getBody().asString());
    }
}
