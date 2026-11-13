@auth
Feature: Authentication

  Scenario: Register a new user and delete the account
    Given I am on the login page
    When I start signing up with a new name and email
    And I fill the account form with:
      | title | password  | day | month | year | firstName | lastName | address   | country | state  | city  | zip   | mobile     |
      | Mr    | Test@1234 | 10  | May   | 1995 | Ali       | Ben      | 1 Main St | Canada  | Quebec | Laval | 12345 | 0612345678 |
    And I click Create Account
    Then I should see "ACCOUNT CREATED!"
    When I click Continue
    Then I should be logged in as my user
    When I delete my account
    Then I should see "ACCOUNT DELETED!"

  @cleanup
  Scenario: Login with a valid account
    Given a registered user exists
    And I am on the login page
    When I log in with my account
    Then I should be logged in as my user

  @cleanup
  Scenario: Logout
    Given a registered user exists
    And I am on the login page
    And I log in with my account
    When I click Logout
    Then I should see the login form

  Scenario: Login with wrong credentials
    Given I am on the login page
    When I log in with email "nobody@nowhere.com" and password "wrongpass"
    Then I should see "Your email or password is incorrect!"

  @cleanup
  Scenario: Signup with an existing email
    Given a registered user exists
    And I am on the login page
    When I start signing up with the existing email
    Then I should see "Email Address already exist!"
