Feature: Character Creation with values

  Background:
    Given I have an attribute with details:
      | ID            | str               |
      | Name          | Strength          |
      | Description   | Makes me stronger |
      | Default Value | 10                |
    And I have an attribute with details:
      | ID            | wis               |
      | Name          | Wisdom            |
      | Description   | Makes me wise     |
      | Default Value | 10                |
    And I have an skill with details:
      | ID            | swords                            |
      | Name          | Swordsmanship                     |
      | Description   | Hitting things with pointy sticks |
      | Default Value | 10                                |
    And I have an skill with details:
      | ID            | clubs                             |
      | Name          | Clubs                             |
      | Description   | Hitting things with blunt sticks  |
      | Default Value | 10                                |
    And I have an ability with details:
      | ID          | powerstrike       |
      | Name        | Power Strike      |
      | Description | Hit things harder |
    And I have an ability with details:
      | ID          | precisestrike              |
      | Name        | Precise Strike             |
      | Description | Hit things more accurately |
    Given I have a User with details:
      | User ID            | 0987654321        |
      | Name               | Test User         |
      | Email              | test@example.com  |
      | Google Provider ID | 1234567890        |
    And I have obtained an Access Token for user "0987654321"

  Scenario: Create Character with no bonus attributes, skills or abilities
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
      | Owner      | 0987654321            |
      | Name       | Bob                   |
      | Race       | human                 |
      | Gender     | male                  |
      | Class      | warrior               |
      | Attributes | str: 10, wis: 10      |
      | Skills     | swords: 10, clubs: 10 |
      | Abilities  |                       |

  Scenario: Create Character with bonus attributes, skills or abilities all from the Race
    Given I have an race with details:
      | ID          | human                      |
      | Name        | Human                      |
      | Description | Pretty much standard       |
      | Attributes  | str: 10, wis: 15           |
      | Skills      | swords: 5, clubs: 7        |
      | Abilities   | powerstrike, precisestrike |
    And I have an gender with details:
      | ID          | male                       |
      | Name        | Male                       |
      | Description | Manly                      |
      | Race        | human                      |
    And I have an class with details:
      | ID          | warrior                    |
      | Name        | Warrior                    |
      | Description | Hits things                |
    When I create a Character with details:
      | Name   | Bob     |
      | Race   | human   |
      | Gender | male    |
      | Class  | warrior |
    Then I get a "Created" response
    And I received character with details:
      | Owner       | 0987654321                  |
      | Name        | Bob                         |
      | Race        | human                       |
      | Gender      | male                        |
      | Class       | warrior                     |
      | Attributes  | str: 20, wis: 25            |
      | Skills      | swords: 15, clubs: 17       |
      | Abilities   | powerstrike, precisestrike  |

  Scenario: Create Character with bonus attributes, skills or abilities all from the Gender
    Given I have an race with details:
      | ID          | human                      |
      | Name        | Human                      |
      | Description | Pretty much standard       |
    And I have an gender with details:
      | ID          | male                       |
      | Name        | Male                       |
      | Description | Manly                      |
      | Race        | human                      |
      | Attributes  | str: 10, wis: 15           |
      | Skills      | swords: 5, clubs: 7        |
      | Abilities   | powerstrike, precisestrike |
    And I have an class with details:
      | ID          | warrior                    |
      | Name        | Warrior                    |
      | Description | Hits things                |
    When I create a Character with details:
      | Name   | Bob     |
      | Race   | human   |
      | Gender | male    |
      | Class  | warrior |
    Then I get a "Created" response
    And I received character with details:
      | Owner       | 0987654321                  |
      | Name        | Bob                         |
      | Race        | human                       |
      | Gender      | male                        |
      | Class       | warrior                     |
      | Attributes  | str: 20, wis: 25            |
      | Skills      | swords: 15, clubs: 17       |
      | Abilities   | powerstrike, precisestrike  |

  Scenario: Create Character with bonus attributes, skills or abilities all from the Class
    Given I have an race with details:
      | ID          | human                      |
      | Name        | Human                      |
      | Description | Pretty much standard       |
    And I have an gender with details:
      | ID          | male                       |
      | Name        | Male                       |
      | Description | Manly                      |
      | Race        | human                      |
    And I have an class with details:
      | ID          | warrior                    |
      | Name        | Warrior                    |
      | Description | Hits things                |
      | Attributes  | str: 10, wis: 15           |
      | Skills      | swords: 5, clubs: 7        |
      | Abilities   | powerstrike, precisestrike |
    When I create a Character with details:
      | Name   | Bob     |
      | Race   | human   |
      | Gender | male    |
      | Class  | warrior |
    Then I get a "Created" response
    And I received character with details:
      | Owner       | 0987654321                  |
      | Name        | Bob                         |
      | Race        | human                       |
      | Gender      | male                        |
      | Class       | warrior                     |
      | Attributes  | str: 20, wis: 25            |
      | Skills      | swords: 15, clubs: 17       |
      | Abilities   | powerstrike, precisestrike  |

  Scenario: Create Character with bonus attributes, skills or abilities from the Race, Gender and Class
    Given I have an race with details:
      | ID          | human                      |
      | Name        | Human                      |
      | Description | Pretty much standard       |
      | Attributes  | str: 10, wis: 15           |
      | Skills      | swords: 5, clubs: 7        |
      | Abilities   | powerstrike                |
    And I have an gender with details:
      | ID          | male                       |
      | Name        | Male                       |
      | Description | Manly                      |
      | Race        | human                      |
      | Attributes  | str: 10, wis: 15           |
      | Skills      | swords: 5, clubs: 7        |
      | Abilities   | precisestrike              |
    And I have an class with details:
      | ID          | warrior                    |
      | Name        | Warrior                    |
      | Description | Hits things                |
      | Attributes  | str: 10, wis: 15           |
      | Skills      | swords: 5, clubs: 7        |
      | Abilities   | precisestrike              |
    When I create a Character with details:
      | Name   | Bob     |
      | Race   | human   |
      | Gender | male    |
      | Class  | warrior |
    Then I get a "Created" response
    And I received character with details:
      | Owner       | 0987654321                  |
      | Name        | Bob                         |
      | Race        | human                       |
      | Gender      | male                        |
      | Class       | warrior                     |
      | Attributes  | str: 40, wis: 55            |
      | Skills      | swords: 25, clubs: 31       |
      | Abilities   | powerstrike, precisestrike  |

