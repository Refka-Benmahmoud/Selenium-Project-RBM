@checkout
Feature: Checkout

  @cleanup
  Scenario: Place an order as a logged-in user
    Given a registered user exists
    And I am on the login page
    And I log in with my account
    And I open the Products page
    And I add product 1 and view the cart
    When I proceed to checkout
    Then the delivery address should match my account
    And the order review should list the added products
    When I place the order with comment "Please deliver fast" and these card details:
      | name  | number           | cvc | month | year |
      | Ali B | 4111111111111111 | 123 | 12    | 2030 |
    Then I should see "Congratulations! Your order has been confirmed!"

  Scenario: Checkout as a guest
    Given I open the Products page
    And I add product 1 and view the cart
    When I proceed to checkout
    Then I should see "Register / Login account to proceed on checkout"
