Feature: Get an Gender

  Scenario: Retrieve an unknown Gender
    When I retrieve the gender "unknown"
    Then I get a "Not Found" response

  Scenario: Retrieve a known Gender
    Given I have an gender with details:
      | ID          | male                     |
      | Name        | Male                     |
      | Description | Manly                    |
      | Race        | human                    |
      | Attributes  | strength: 10, wisdom: 20 |
      | Skills      | swords: 5, clubs: 10     |
      | Abilities   | powerstrike, cleave      |
    When I retrieve the gender "male"
    Then I get an "OK" response
    And I received gender:
      | ID          | male                     |
      | Name        | Male                     |
      | Description | Manly                    |
      | Race        | human                    |
      | Attributes  | strength: 10, wisdom: 20 |
      | Skills      | swords: 5, clubs: 10     |
      | Abilities   | powerstrike, cleave      |
