@web @product
Feature: Product Management

  As a logged in user
  I want to manage products
  So that I can browse and shop items

  Background:
    Given the user is logged in

  ### ========== VIEW CATEGORY ========== ###
  Scenario: View products by category
    When the user selects the "Laptops" category
    Then products under "Laptops" should be displayed

  ### ========== VIEW PRODUCT DETAIL ========== ###
  Scenario: View product details
    When the user selects product "Sony vaio i5"
    Then product detail page for "Sony vaio i5" should be displayed

  ### ========== ADD PRODUCT TO CART ========== ###
  Scenario Outline: Add product to cart
    When the user selects product "<product>"
    Then product detail page for "<product>" should be displayed
    And the user adds the product to the cart
    Then an alert message "Product added." should be displayed
    Examples:
      | product       |
      | HTC One M9    |
      | Sony vaio i7  |

  ### ========== REMOVE PRODUCT FROM CART ========== ###
  Scenario Outline: Remove product from cart
    When the user adds product "<product>" to the cart
    And the user opens the cart page
    And the user removes product "<product>" from the cart
    Then product "<product>" should not be visible in the cart
    Examples:
      | product         |
      | Sony xperia z5  |