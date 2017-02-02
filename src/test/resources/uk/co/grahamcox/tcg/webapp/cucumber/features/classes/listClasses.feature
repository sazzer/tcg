Feature: Listing Classes

  Scenario: No Classes to list
    When I retrieve the list of classes
    Then I get an "OK" response
    And I get 0 of 0 results returned, starting at offset 0

  Scenario: Bad Sort Order
    When I retrieve the list of classes:
      | Sort | Unknpwn |
    Then I get a "Bad Request" response

  Scenario: Listing the only Class
    Given I have an class with details:
      | ID          | warrior     |
      | Name        | Warrior     |
      | Description | Hits things |
    When I retrieve the list of classes
    Then I get an "OK" response
    And I get 1 of 1 results returned, starting at offset 0
    And class 1 has details:
      | ID          | warrior     |
      | Name        | Warrior     |
      | Description | Hits things |

  Scenario: Listing the only two Classes, sorted by Name
    Given I have an class with details:
      | ID          | warrior     |
      | Name        | Warrior     |
      | Description | Hits things |
    And I have an class with details:
      | ID          | mage         |
      | Name        | Mage         |
      | Description | Casts spells |
    When I retrieve the list of classes:
      | Sort | Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And class 1 has details:
      | ID          | mage         |
      | Name        | Mage         |
      | Description | Casts spells |
    And class 2 has details:
      | ID          | warrior     |
      | Name        | Warrior     |
      | Description | Hits things |

  Scenario: Listing the only two Classes, sorted by Name Descending
    Given I have an class with details:
      | ID          | warrior     |
      | Name        | Warrior     |
      | Description | Hits things |
    And I have an class with details:
      | ID          | mage         |
      | Name        | Mage         |
      | Description | Casts spells |
    When I retrieve the list of classes:
      | Sort | -Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And class 1 has details:
      | ID          | warrior     |
      | Name        | Warrior     |
      | Description | Hits things |
    And class 2 has details:
      | ID          | mage         |
      | Name        | Mage         |
      | Description | Casts spells |

  Scenario: Listing the second of two Classes, sorted by Name
    Given I have an class with details:
      | ID          | warrior     |
      | Name        | Warrior     |
      | Description | Hits things |
    And I have an class with details:
      | ID          | mage         |
      | Name        | Mage         |
      | Description | Casts spells |
    When I retrieve the list of classes:
      | Offset | 1    |
      | Sort   | Name |
    Then I get an "OK" response
    And I get 1 of 2 results returned, starting at offset 1
    And class 1 has details:
      | ID          | warrior     |
      | Name        | Warrior     |
      | Description | Hits things |
