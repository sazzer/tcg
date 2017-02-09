Feature: Get an Attribute

  Scenario: Retrieve an unknown Attribute
    When I retrieve the attribute "unknown"
    Then I get a "Not Found" response

  Scenario: Retrieve a known Attribute
    Given I have an attribute with details:
      | ID            | str               |
      | Name          | Strength          |
      | Description   | Makes me stronger |
      | Default Value | 25                |
    When I retrieve the attribute "str"
    Then I get an "OK" response
    And I received attribute:
      | ID            | str               |
      | Name          | Strength          |
      | Description   | Makes me stronger |
      | Default Value | 25                |
