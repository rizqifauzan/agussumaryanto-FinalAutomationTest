@api
Feature: DummyAPI.io API Testing
  Testing DummyAPI.io endpoints with valid and invalid cases

  Background:
    Given the API base url "https://dummyapi.io/data/v1"
    And a valid app-id

  # ---------------------------------
  # VALID API TESTS
  # ---------------------------------
  @valid-api
  Scenario: Get List of Users
    When the user sends GET request to "/user"
    Then the response code should be 200
    And the response should contain "data"

  @valid-api
  Scenario: Get User by ID
    Given a valid user id "60d0fe4f5311236168a109f3"
    When the user sends GET request to "/user/{id}"
    Then the response code should be 200
    And the response should contain "id"

  @valid-api
  Scenario: Create User
    When the user sends POST request to "/user/create" with body:
      """
      {
        "title": "mr",
        "firstName": "Agus",
        "lastName": "Sumaryanto",
        "email": "<unique_email>",
        "gender": "male"
      }
      """
    Then the response code should be 201
    And the response should contain "id"

  @valid-api
  Scenario: Update User
    When the user sends PUT request to "/user/{id}" with body:
      """
      { "firstName": "AgusUpdate" }
      """
    Then the response code should be 200
    And the response should contain "AgusUpdate"

  @valid-api
  Scenario: Delete User
    When the user sends DELETE request to "/user/{id}"
    Then the response code should be 204
    And the delete response should be successful

  @valid-api
  Scenario: Get List of Tags
    When the user sends GET request to "/tag"
    Then the response code should be 200
    And the response should contain "data"

  # ---------------------------------
  # INVALID API TESTS (WITHOUT BODY)
  # ---------------------------------
  @invalid-api
  Scenario Outline: Invalid API usage
    Given <precondition>
    When the user sends <method> request to "<endpoint>"
    Then the response code should be <status>
    And the response should contain error "<error>"

    Examples:
      | precondition                       | method  | endpoint           | status  | error               |
      | no app-id                          | GET     | /user              | 403     | APP_ID_MISSING      |
      | an app-id "invalidAppId123"        | GET     | /user              | 403     | APP_ID_NOT_EXIST    |
      | an invalid user id "invalidId123"  | GET     | /user/{id}         | 404     | RESOURCE_NOT_FOUND  |
      | invalid endpoint                   | GET     | /unknownEndpoint   | 404     | PATH_NOT_FOUND      |
      | an invalid user id "invalidId123"  | DELETE  | /user/{id}         | 404     | RESOURCE_NOT_FOUND  |

  # ---------------------------------
  # INVALID API TESTS (WITH BODY)
  # ---------------------------------
  @invalid-api
  Scenario Outline: Create User with invalid body
    Given <precondition>
    When the user sends POST request to "/user/create" with body:
    """
    <bodypart>
    """
    Then the response code should be <status>
    And the response should contain error "<error>"

    Examples:
      | precondition             | bodypart                                                 | status | error          |
      | Create User no firstName | { "lastName":"Test", "email":"invalid@test.com" }        | 400    | BODY_NOT_VALID |
      | Create User no lastName  | { "firstName":"Agus", "email":"invalid@test.com" }       | 400    | BODY_NOT_VALID |
      | Create User no email     | { "firstName":"Agus", "lastName":"Test" }                | 400    | BODY_NOT_VALID |
      | Create User invalid email| { "firstName":"Agus", "lastName":"Test", "email":"xx" }  | 400    | BODY_NOT_VALID |