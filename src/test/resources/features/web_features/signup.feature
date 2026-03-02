@web @signup
Feature: User Signup

  As a visitor
  I want to create an account
  So that I can log in to the application

  Background:
    Given the user is on the homepage

  ### ========== POSITIVE ========== ###
  @positive
  Scenario Outline: Successful registration
    When the user clicks the "sign up" button
    And the user submits registration with unique username "<username>" and password "<password>"
    Then an alert message "<message>" should be displayed
    Examples:
      | username | password     | message             |
      | user     | Password123! | Sign up successful. |


  ### ========== NEGATIVE - DUPLICATE ========== ###
  @negative @duplicate
  Scenario Outline: Failed registration - duplicate username
    Given the registration precondition for username "<username>"
    When the user clicks the "sign up" button
    And the user submits registration with username "<username>" and password "<password>"
    Then an alert message "<message>" should be displayed
    Examples:
      | username   | password | message                      |
      | dupUser123 | Password | This user already exist.     |


  ### ========== NEGATIVE - EMPTY FIELD ========== ###
  @negative @empty
  Scenario Outline: Failed registration - empty field
    When the user clicks the "sign up" button
    And the user submits registration with username "<username>" and password "<password>"
    Then an alert message "<message>" should be displayed
    Examples:
      | username | password | message                               |
      |          |          | Please fill out Username and Password.|