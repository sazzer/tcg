Feature: Get a statistic

  Scenario: Retrieve an unknown statistic
    When I retrieve the stat "unknown"
    Then I get a "Not Found" response

  Scenario: Retrieve a known statistic
    Given I have a Statistic with details:
      | ID          | str               |
      | Name        | Strength          |
      | Description | Makes me stronger |
    When I retrieve the stat "str"
    Then I get an "OK" response
    And I received statistic:
      | ID          | str               |
      | Name        | Strength          |
      | Description | Makes me stronger |
