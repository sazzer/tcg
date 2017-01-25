Feature: Get the currently logged in user

  Scenario: Try to get the user when not logged in
    When I retrieve the currently logged in user
    Then I get an "Unauthorized" response

  Scenario: Try to get the user when I am logged in
    Given I have a User with details:
      | User ID            | 0987654321       |
      | Name               | Test User        |
      | Email              | test@example.com |
      | Google Provider ID | 1234567890       |
    And I have obtained an Access Token for user "0987654321"
    When I retrieve the currently logged in user
    Then I get an "OK" response
    And I received user details:
      | User ID            | 0987654321       |
      | Name               | Test User        |
      | Email              | test@example.com |
