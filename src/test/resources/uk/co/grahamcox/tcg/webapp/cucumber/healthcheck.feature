@wip
Feature: Healthchecks
  Scenario: Checking the healthchecks
    When I check the health of the system
    Then the system is Up
    And there are 0 Neo4j Nodes in the database