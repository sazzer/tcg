Feature: Get an Race

  Scenario: Retrieve an unknown Race
    When I retrieve the race "unknown"
    Then I get a "Not Found" response

  Scenario: Retrieve a known Race
    Given I have an race with details:
      | ID          | human                    |
      | Name        | Human                    |
      | Description | Pretty much standard     |
      | Attributes  | strength: 10, wisdom: 20 |
      | Skills      | swords: 5, clubs: 10     |
    When I retrieve the race "human"
    Then I get an "OK" response
    And I received race:
      | ID          | human                    |
      | Name        | Human                    |
      | Description | Pretty much standard     |
      | Attributes  | strength: 10, wisdom: 20 |
      | Skills      | swords: 5, clubs: 10     |
