Feature: Get an Race

  Scenario: Retrieve an unknown Race
    When I retrieve the race "unknown"
    Then I get a "Not Found" response

  Scenario: Retrieve a known Race
    Given I have an race with details:
      | ID          | human                |
      | Name        | Human                |
      | Description | Pretty much standard |
    When I retrieve the race "human"
    Then I get an "OK" response
    And I received race:
      | ID          | human                |
      | Name        | Human                |
      | Description | Pretty much standard |
