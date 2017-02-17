Feature: Character Creation with no values

  Background:
    Given I have a User with details:
      | User ID            | 0987654321        |
      | Name               | Test User         |
      | Email              | test@example.com  |
      | Google Provider ID | 1234567890        |
    And I have obtained an Access Token for user "0987654321"

  Scenario: Create Character with no attributes, skills or abilities
    Given I have an race with details:
      | ID          | human                    |
      | Name        | Human                    |
      | Description | Pretty much standard     |
    And I have an gender with details:
      | ID          | male                     |
      | Name        | Male                     |
      | Description | Manly                    |
      | Race        | human                    |
    And I have an class with details:
      | ID          | warrior                  |
      | Name        | Warrior                  |
      | Description | Hits things              |
    When I create a Character with details:
      | Name   | Bob     |
      | Race   | human   |
      | Gender | male    |
      | Class  | warrior |
    Then I get a "Created" response
    And I received character with details:
      | Owner      | 0987654321 |
      | Name       | Bob        |
      | Race       | human      |
      | Gender     | male       |
      | Class      | warrior    |
      | Attributes |            |
      | Skills     |            |
      | Abilities  |            |