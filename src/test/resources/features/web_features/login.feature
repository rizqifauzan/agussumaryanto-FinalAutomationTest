@web @login
Feature: User Login

  As a registered user
  I want to log in
  So that I can access my account

  Background:
    Given the user is on the homepage

  ### ========== POSITIVE ========== ###
  @positive
  Scenario Outline: Successful login
    # Make sure the user is registered before logging in.
    Given the registration precondition for username "<username>"
    When the user clicks the "login" button
    And the user login with username "<username>" and password "<password>"
    Then the username "<username>" should be visible in the navbar
    Examples:
      | username       | password        |
      | agussumaryanto | agussumaryanto  |

  ### ========== NEGATIVE - WRONG PASSWORD ========== ###
  @negative @wrongpassword
  Scenario Outline: Failed login - wrong password
    When the user clicks the "login" button
    And the user login with username "<username>" and password "<password>"
    Then an alert message "<message>" should be displayed
    Examples:
      | username | password   | message         |
      | user123  | WrongPass! | Wrong password. |

  ### ========== NEGATIVE - EMPTY FIELD ========== ###
  @negative @empty
  Scenario Outline: Failed login - empty field
    When the user clicks the "login" button
    And the user login with username "<username>" and password "<password>"
    Then an alert message "<message>" should be displayed
    Examples:
      | username | password | message                               |
      |          |          | Please fill out Username and Password.|