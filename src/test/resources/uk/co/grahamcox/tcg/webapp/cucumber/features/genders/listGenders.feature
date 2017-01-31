Feature: Listing Genders

  Scenario: No Genders to list
    When I retrieve the list of genders
    Then I get an "OK" response
    And I get 0 of 0 results returned, starting at offset 0

  Scenario: Bad Sort Order
    When I retrieve the list of genders:
      | Sort | Unknpwn |
    Then I get a "Bad Request" response

  Scenario: Listing the only Gender
    Given I have an gender with details:
      | ID          | male  |
      | Name        | Male  |
      | Description | Manly |
      | Race        | human |
    When I retrieve the list of genders
    Then I get an "OK" response
    And I get 1 of 1 results returned, starting at offset 0
    And gender 1 has details:
      | ID          | male  |
      | Name        | Male  |
      | Description | Manly |
      | Race        | human |

  Scenario: Listing the only two Genders, sorted by Name
    Given I have an gender with details:
      | ID          | male  |
      | Name        | Male  |
      | Description | Manly |
      | Race        | human |
    And I have an gender with details:
      | ID          | female  |
      | Name        | Female  |
      | Description | Womanly |
      | Race        | human   |
    When I retrieve the list of genders:
      | Sort | Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And gender 1 has details:
      | ID          | female  |
      | Name        | Female  |
      | Description | Womanly |
      | Race        | human   |
    And gender 2 has details:
      | ID          | male  |
      | Name        | Male  |
      | Description | Manly |
      | Race        | human |

  Scenario: Listing the only two Genders, sorted by Name Descending
    Given I have an gender with details:
      | ID          | male  |
      | Name        | Male  |
      | Description | Manly |
      | Race        | human |
    And I have an gender with details:
      | ID          | female  |
      | Name        | Female  |
      | Description | Womanly |
      | Race        | human   |
    When I retrieve the list of genders:
      | Sort | -Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And gender 1 has details:
      | ID          | male  |
      | Name        | Male  |
      | Description | Manly |
      | Race        | human |
    And gender 2 has details:
      | ID          | female  |
      | Name        | Female  |
      | Description | Womanly |
      | Race        | human   |

  Scenario: Listing the second of two Genders, sorted by Name
    Given I have an gender with details:
      | ID          | male  |
      | Name        | Male  |
      | Description | Manly |
      | Race        | human |
    And I have an gender with details:
      | ID          | female  |
      | Name        | Female  |
      | Description | Womanly |
      | Race        | human   |
    When I retrieve the list of genders:
      | Offset | 1    |
      | Sort   | Name |
    Then I get an "OK" response
    And I get 1 of 2 results returned, starting at offset 1
    And gender 1 has details:
      | ID          | male  |
      | Name        | Male  |
      | Description | Manly |
      | Race        | human |

  Scenario: Listing genders filtered by race
    Given I have an gender with details:
      | ID          | humanmale  |
      | Name        | Male       |
      | Description | Manly      |
      | Race        | human      |
    And I have an gender with details:
      | ID          | dwarfmale  |
      | Name        | Male       |
      | Description | Hairy      |
      | Race        | dwarf      |
    When I retrieve the list of genders:
      | Sort   | Name  |
      | Race   | human |
    Then I get an "OK" response
    And I get 1 of 1 results returned, starting at offset 0
    And gender 1 has details:
      | ID          | humanmale  |
      | Name        | Male       |
      | Description | Manly      |
      | Race        | human      |
