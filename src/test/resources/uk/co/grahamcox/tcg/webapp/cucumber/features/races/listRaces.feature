Feature: Listing Races

  Scenario: No Races to list
    When I retrieve the list of races
    Then I get an "OK" response
    And I get 0 of 0 results returned, starting at offset 0

  Scenario: Bad Sort Order
    When I retrieve the list of races:
      | Sort | Unknpwn |
    Then I get a "Bad Request" response

  Scenario: Listing the only Race
    Given I have an race with details:
      | ID          | human                |
      | Name        | Human                |
      | Description | Pretty much standard |
    When I retrieve the list of races
    Then I get an "OK" response
    And I get 1 of 1 results returned, starting at offset 0
    And race 1 has details:
      | ID          | human                |
      | Name        | Human                |
      | Description | Pretty much standard |

  Scenario: Listing the only two Races, sorted by Name
    Given I have an race with details:
      | ID          | human                |
      | Name        | Human                |
      | Description | Pretty much standard |
    And I have an race with details:
      | ID          | dwarf                |
      | Name        | Dwarf                |
      | Description | Much shorter         |
    When I retrieve the list of races:
      | Sort | Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And race 1 has details:
      | ID          | dwarf                |
      | Name        | Dwarf                |
      | Description | Much shorter         |
    And race 2 has details:
      | ID          | human                |
      | Name        | Human                |
      | Description | Pretty much standard |

  Scenario: Listing the only two Races, sorted by Name Descending
    Given I have an race with details:
      | ID          | human                |
      | Name        | Human                |
      | Description | Pretty much standard |
    And I have an race with details:
      | ID          | dwarf                |
      | Name        | Dwarf                |
      | Description | Much shorter         |
    When I retrieve the list of races:
      | Sort | -Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And race 1 has details:
      | ID          | human                |
      | Name        | Human                |
      | Description | Pretty much standard |
    And race 2 has details:
      | ID          | dwarf                |
      | Name        | Dwarf                |
      | Description | Much shorter         |

  Scenario: Listing the second of two Races, sorted by Name
    Given I have an race with details:
      | ID          | human                |
      | Name        | Human                |
      | Description | Pretty much standard |
    And I have an race with details:
      | ID          | dwarf                |
      | Name        | Dwarf                |
      | Description | Much shorter         |
    When I retrieve the list of races:
      | Offset | 1    |
      | Sort   | Name |
    Then I get an "OK" response
    And I get 1 of 2 results returned, starting at offset 1
    And race 1 has details:
      | ID          | human                |
      | Name        | Human                |
      | Description | Pretty much standard |
