Feature: Healthchecks
  Scenario: Checking the healthchecks with an empty database
    When I check the health of the system
    Then the system is healthy
