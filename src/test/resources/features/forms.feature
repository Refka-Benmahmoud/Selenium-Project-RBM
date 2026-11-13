@forms
Feature: Forms

  Scenario: Contact us form
    When I open the Contact us page
    And I fill the contact form
    And I attach a small file
    And I submit the form and accept the alert
    Then I should see "Success! Your details have been submitted successfully."

  Scenario: Newsletter subscription
    Given I am on the home page
    When I subscribe to the newsletter with a new email
    Then I should see "You have been successfully subscribed!"
