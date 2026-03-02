package api.pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiBasePage {
    protected String baseUrl;
    protected String appId;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Response getRequest(String endpoint) {
        if (appId == null) {
            // without app-id header
            return given()
                    .baseUri(baseUrl)
                    .when()
                    .get(endpoint)
                    .then()
                    .extract()
                    .response();
        } else {
            return given()
                    .baseUri(baseUrl)
                    .header("app-id", appId)
                    .when()
                    .get(endpoint)
                    .then()
                    .extract()
                    .response();
        }
    }

    @SuppressWarnings("SameParameterValue") // ignoring the warnings
    protected Response postRequest(String endpoint, String body) {
        return given()
                .header("app-id", appId)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint);
    }

    protected Response putRequest(String endpoint, String body) {
        return given()
                .header("app-id", appId)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(endpoint);
    }

    protected Response deleteRequest(String endpoint) {
        return given()
                .header("app-id", appId)
                .when()
                .delete(endpoint);
    }
}
