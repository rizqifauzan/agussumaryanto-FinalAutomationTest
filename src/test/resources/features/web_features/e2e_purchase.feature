@web @e2e
Feature: Complete Purchase Flow

  As a new user
  I want to complete a full purchase journey
  So that I can register, login, buy a product, and logout successfully

  Scenario: User completes full purchase journey
    Given the user is on the homepage

    # REGISTER (UNIQUE USERNAME)
    When the user clicks the "sign up" button
    And the user submits registration with unique username "newUser123" and password "Password123!"

      # LOGIN
    And the user clicks the "login" button
    And the user login with the registered username and password "Password123!"

    # ADD PRODUCT
    And the user selects product "Nokia lumia 1520"
    And the user adds the product to the cart
    And the user navigates to cart
    And the user clicks place order

    # CHECKOUT
    And the user completes checkout with:
      | name        | Agus       |
      | country     | Indonesia  |
      | city        | Bogor      |
      | credit card | 1234567890 |
      | month       | 2          |
      | year        | 2026       |
    And the user clicks the "Purchase" button to pay

    # SUCCESS VALIDATION
    Then a success message "Thank you for your purchase!" should be displayed
    And the user confirms purchase

    # LOGOUT
    And the user clicks the "logout" button