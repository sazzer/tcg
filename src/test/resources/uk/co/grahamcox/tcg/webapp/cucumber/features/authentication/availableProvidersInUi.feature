@UI
Feature: The correct authentication providers appear in the UI
  Scenario: Check the correct authentication providers appear in the UI
    Given I have loaded the home page
    Then the Login menu is not present
    And the User menu is not present
    And a single Login button is present:
      | Label    | Log in with Google+ |
      | Provider | google              |