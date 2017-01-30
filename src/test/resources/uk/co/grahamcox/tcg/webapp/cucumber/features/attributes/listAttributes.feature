Feature: Listing Attributes

  Scenario: No Attributes to list
    When I retrieve the list of attributes
    Then I get an "OK" response
    And I get 0 of 0 results returned, starting at offset 0

  Scenario: Bad Sort Order
    When I retrieve the list of attributes:
      | Sort | Unknpwn |
    Then I get a "Bad Request" response

  Scenario: Listing the only Attribute
    Given I have an attribute with details:
      | ID          | str               |
      | Name        | Strength          |
      | Description | Makes me stronger |
    When I retrieve the list of attributes
    Then I get an "OK" response
    And I get 1 of 1 results returned, starting at offset 0
    And attribute 1 has details:
      | ID          | str               |
      | Name        | Strength          |
      | Description | Makes me stronger |

  Scenario: Listing the only two Attributes, sorted by Name
    Given I have an attribute with details:
      | ID          | str               |
      | Name        | Strength          |
      | Description | Makes me stronger |
    And I have an attribute with details:
      | ID          | wis               |
      | Name        | Wisdom            |
      | Description | Makes me wise     |
    When I retrieve the list of attributes:
      | Sort | Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And attribute 1 has details:
      | ID          | str               |
      | Name        | Strength          |
      | Description | Makes me stronger |
    And attribute 2 has details:
      | ID          | wis               |
      | Name        | Wisdom            |
      | Description | Makes me wise     |

  Scenario: Listing the only two Attributes, sorted by Name Descending
    Given I have an attribute with details:
      | ID          | str               |
      | Name        | Strength          |
      | Description | Makes me stronger |
    And I have an attribute with details:
      | ID          | wis               |
      | Name        | Wisdom            |
      | Description | Makes me wise     |
    When I retrieve the list of attributes:
      | Sort | -Name |
    Then I get an "OK" response
    And I get 2 of 2 results returned, starting at offset 0
    And attribute 1 has details:
      | ID          | wis               |
      | Name        | Wisdom            |
      | Description | Makes me wise     |
    And attribute 2 has details:
      | ID          | str               |
      | Name        | Strength          |
      | Description | Makes me stronger |

  Scenario: Listing the second of two Attributes, sorted by Name
    Given I have an attribute with details:
      | ID          | str               |
      | Name        | Strength          |
      | Description | Makes me stronger |
    And I have an attribute with details:
      | ID          | wis               |
      | Name        | Wisdom            |
      | Description | Makes me wise     |
    When I retrieve the list of attributes:
      | Offset | 1    |
      | Sort   | Name |
    Then I get an "OK" response
    And I get 1 of 2 results returned, starting at offset 1
    And attribute 1 has details:
      | ID          | wis               |
      | Name        | Wisdom            |
      | Description | Makes me wise     |
