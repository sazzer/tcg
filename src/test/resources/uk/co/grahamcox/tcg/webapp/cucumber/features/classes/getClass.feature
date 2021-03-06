Feature: Get a Class

  Scenario: Retrieve an unknown Class
    When I retrieve the class "unknown"
    Then I get a "Not Found" response

  Scenario: Retrieve a known Class
    Given I have an class with details:
      | ID          | warrior                  |
      | Name        | Warrior                  |
      | Description | Hits things              |
      | Attributes  | strength: 10, wisdom: 20 |
      | Skills      | swords: 5, clubs: 10     |
      | Abilities   | powerstrike, cleave      |
    When I retrieve the class "warrior"
    Then I get an "OK" response
    And I received class:
      | ID          | warrior                  |
      | Name        | Warrior                  |
      | Description | Hits things              |
      | Attributes  | strength: 10, wisdom: 20 |
      | Skills      | swords: 5, clubs: 10     |
      | Abilities   | powerstrike, cleave      |
