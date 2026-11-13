@cart
Feature: Shopping cart

  Scenario: Add two products
    Given I open the Products page
    When I add product 1 and continue shopping
    And I add product 2 and view the cart
    Then the cart should contain 2 products
    And the cart should list the added products
    And each cart row should have price multiplied by quantity equal to total

  Scenario: Choose a quantity
    Given I open the Products page
    When I open the details of the first product
    And I set the quantity to 4 and add to cart
    And I view the cart
    Then the quantity in the cart should be 4

  Scenario: Remove a product
    Given I open the Products page
    And I add product 1 and view the cart
    When I remove the first product from the cart
    Then the product should disappear from the cart
    And I should see "Cart is empty!"
