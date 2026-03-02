package api.pages;

import io.restassured.response.Response;

public class TagApiPage extends ApiBasePage {

    public Response getAllTags() {
        return getRequest("/tag");
    }
}
