Feature: Healthchecks
  Scenario: Checking the healthchecks with an empty database
    When I check the health of the system
    Then the system is healthy
    And there are "0" nodes in the database

  Scenario: Checking the healthchecks with a populated database
    Given I have a User with details:
      | User ID            | 0987654321       |
      | Name               | Test User        |
      | Email              | test@example.com |
      | Google Provider ID | 1234567890       |
    When I check the health of the system
    Then the system is healthy
    And there are "2" nodes in the database
    # One for the User, one for the "google" Provider
