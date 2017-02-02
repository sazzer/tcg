Feature: Listing Skills

  Scenario: No Skills to list
    When I retrieve the list of skills
    Then I get an "OK" response
    And I get 0 of 0 results returned, starting at offset 0

  Scenario: Bad Sort Order
    When I retrieve the list of skills:
      | Sort | Unknpwn |
    Then I get a "Bad Request" response

  Scenario: Listing the only Skill
    Given I have an skill with details:
      | ID          | swords                            |
      | Name        | Swordsmanship                     |
      | Description | Hitting things with pointy sticks |
    When I retrieve the list of skills
    Then I get an "OK" response
    And I get 1 of 1 results returned, starting at offset 0
    And skill 1 has details:
      | ID          | swords                            |
      | Name        | Swordsmanship                     |
      | Description | Hitting things with pointy sticks |

  Scenario: Listing the only two Skills, sorted by Name
    Given I have an skill with details:
      | ID          | swords                            |
      | Name        | Swordsmanship                     |
      | Description | Hitting things with pointy sticks |
    And I have an skill with details:
      | ID          | clubs                             |
      | Name        | Clubs                             |
      | Description | Hitting things with blunt sticks  |
    When I retrieve the list of skills:
      | Sort | Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And skill 1 has details:
      | ID          | clubs                             |
      | Name        | Clubs                             |
      | Description | Hitting things with blunt sticks  |
    And skill 2 has details:
      | ID          | swords                            |
      | Name        | Swordsmanship                     |
      | Description | Hitting things with pointy sticks |

  Scenario: Listing the only two Skills, sorted by Name Descending
    Given I have an skill with details:
      | ID          | swords                            |
      | Name        | Swordsmanship                     |
      | Description | Hitting things with pointy sticks |
    And I have an skill with details:
      | ID          | clubs                             |
      | Name        | Clubs                             |
      | Description | Hitting things with blunt sticks  |
    When I retrieve the list of skills:
      | Sort | -Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And skill 1 has details:
      | ID          | swords                            |
      | Name        | Swordsmanship                     |
      | Description | Hitting things with pointy sticks |
    And skill 2 has details:
      | ID          | clubs                             |
      | Name        | Clubs                             |
      | Description | Hitting things with blunt sticks  |

  Scenario: Listing the second of two Skills, sorted by Name
    Given I have an skill with details:
      | ID          | swords                            |
      | Name        | Swordsmanship                     |
      | Description | Hitting things with pointy sticks |
    And I have an skill with details:
      | ID          | clubs                             |
      | Name        | Clubs                             |
      | Description | Hitting things with blunt sticks  |
    When I retrieve the list of skills:
      | Offset | 1    |
      | Sort   | Name |
    Then I get an "OK" response
    And I get 1 of 2 results returned, starting at offset 1
    And skill 1 has details:
      | ID          | swords                            |
      | Name        | Swordsmanship                     |
      | Description | Hitting things with pointy sticks |
