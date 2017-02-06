Feature: Listing Abilities

  Scenario: No Abilities to list
    When I retrieve the list of abilities
    Then I get an "OK" response
    And I get 0 of 0 results returned, starting at offset 0

  Scenario: Bad Sort Order
    When I retrieve the list of abilities:
      | Sort | Unknpwn |
    Then I get a "Bad Request" response

  Scenario: Listing the only Ability
    Given I have an ability with details:
      | ID          | powerstrike       |
      | Name        | Power Strike      |
      | Description | Hit things harder |
    When I retrieve the list of abilities
    Then I get an "OK" response
    And I get 1 of 1 results returned, starting at offset 0
    And ability 1 has details:
      | ID          | powerstrike       |
      | Name        | Power Strike      |
      | Description | Hit things harder |

  Scenario: Listing the only two Abilities, sorted by Name
    Given I have an ability with details:
      | ID          | powerstrike       |
      | Name        | Power Strike      |
      | Description | Hit things harder |
    And I have an ability with details:
      | ID          | precisestrike              |
      | Name        | Precise Strike             |
      | Description | Hit things more accurately |
    When I retrieve the list of abilities:
      | Sort | Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And ability 1 has details:
      | ID          | powerstrike       |
      | Name        | Power Strike      |
      | Description | Hit things harder |
    And ability 2 has details:
      | ID          | precisestrike              |
      | Name        | Precise Strike             |
      | Description | Hit things more accurately |

  Scenario: Listing the only two Abilities, sorted by Name Descending
    Given I have an ability with details:
      | ID          | powerstrike       |
      | Name        | Power Strike      |
      | Description | Hit things harder |
    And I have an ability with details:
      | ID          | precisestrike              |
      | Name        | Precise Strike             |
      | Description | Hit things more accurately |
    When I retrieve the list of abilities:
      | Sort | -Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And ability 1 has details:
      | ID          | precisestrike              |
      | Name        | Precise Strike             |
      | Description | Hit things more accurately |
    And ability 2 has details:
      | ID          | powerstrike       |
      | Name        | Power Strike      |
      | Description | Hit things harder |

  Scenario: Listing the second of two Abilities, sorted by Name
    Given I have an ability with details:
      | ID          | powerstrike       |
      | Name        | Power Strike      |
      | Description | Hit things harder |
    And I have an ability with details:
      | ID          | precisestrike              |
      | Name        | Precise Strike             |
      | Description | Hit things more accurately |
    When I retrieve the list of abilities:
      | Offset | 1    |
      | Sort   | Name |
    Then I get an "OK" response
    And I get 1 of 2 results returned, starting at offset 1
    And ability 1 has details:
      | ID          | precisestrike              |
      | Name        | Precise Strike             |
      | Description | Hit things more accurately |
