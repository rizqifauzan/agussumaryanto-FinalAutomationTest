@web @payment
Feature: Checkout and Payment

  As a logged in user
  I want to complete checkout
  So that I can purchase products

  Background:
    Given the user is logged in
    And the user adds product "Nexus 6" to the cart
    And the user opens the cart page

  @positive
  Scenario: Successful payment
    When the user clicks the "Place Order" button
    And the user completes checkout with:
      | name        | Agus       |
      | country     | Indonesia  |
      | city        | Bogor      |
      | credit card | 1234567890 |
      | month       | 9          |
      | year        | 2025       |
    And the user clicks the "Purchase" button to pay
    Then a success message "Thank you for your purchase!" should be displayed

  @negative
  Scenario: Payment with missing required fields
    When the user clicks the "Place Order" button
    And the user completes checkout with empty required fields
    And the user clicks the "Purchase" button to pay
    Then an alert message "Please fill out Name and Creditcard." should be displayed