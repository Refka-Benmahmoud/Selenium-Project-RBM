@products
Feature: Products

  Scenario: All products page
    When I open the Products page
    Then I should see "ALL PRODUCTS"
    And the product list should not be empty

  Scenario: Product details
    Given I open the Products page
    When I open the details of the first product
    Then I should see the name, category, price, availability, condition and brand

  Scenario Outline: Search for a product
    Given I open the Products page
    When I search for "<word>"
    Then I should see "SEARCHED PRODUCTS"
    And every result should contain "<word>"

    Examples:
      | word  |
      | top   |
      | dress |
      | jeans |

  Scenario: Browse by category
    Given I open the Products page
    When I choose the category "Women" then "Dress"
    Then I should see "WOMEN - DRESS PRODUCTS"
