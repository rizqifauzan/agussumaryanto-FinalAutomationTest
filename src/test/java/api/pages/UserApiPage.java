package api.pages;

import io.restassured.response.Response;

public class UserApiPage extends ApiBasePage {

    public Response getAllUsers() {
        return getRequest("/user");
    }

    public Response getUserById(String id) {
        return getRequest("/user/" + id);
    }

    public Response createUser(String body) {
        return postRequest("/user/create", body);
    }

    public Response updateUser(String id, String body) {
        return putRequest("/user/" + id, body);
    }

    public Response deleteUser(String id) {
        return deleteRequest("/user/" + id);
    }

}
