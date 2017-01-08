@wip
Feature: Healthchecks
  Scenario: Checking the healthchecks with an empty database
    When I check the health of the system
    Then the healthchecks indicate that the system is Up
    And there are 0 Neo4j Nodes in the database

  Scenario: Checking the healthchecks with nodes in the database
    Given I have an arbitrary node in the database
    When I check the health of the system
    Then the healthchecks indicate that the system is Up
    And there is 1 Neo4j Node in the database