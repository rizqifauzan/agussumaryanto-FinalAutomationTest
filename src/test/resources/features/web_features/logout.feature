@web @logout
Feature: User Logout

  As a logged in user
  I want to log out
  So that my session ends securely

  Background:
    Given the user is logged in

  @positive
  Scenario: Successful logout
    When the user clicks the "logout" button
    Then the login button should be visible
    And the logout button should not be visible

  @negative
  Scenario Outline: Logout button not visible when user is not logged in
    When the user clicks the "logout" button
    Then the username "<username>" is still visible in the navbar
    Examples:
      | username       |
      | agussumaryanto |