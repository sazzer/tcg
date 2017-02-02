Feature: Get an Skill

  Scenario: Retrieve an unknown Skill
    When I retrieve the skill "unknown"
    Then I get a "Not Found" response

  Scenario: Retrieve a known Skill
    Given I have an skill with details:
      | ID          | swords                            |
      | Name        | Swordsmanship                     |
      | Description | Hitting things with pointy sticks |
    When I retrieve the skill "swords"
    Then I get an "OK" response
    And I received skill:
      | ID          | swords                            |
      | Name        | Swordsmanship                     |
      | Description | Hitting things with pointy sticks |
