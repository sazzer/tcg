Feature: Get a Class

  Scenario: Retrieve an unknown Class
    When I retrieve the class "unknown"
    Then I get a "Not Found" response

  Scenario: Retrieve a known Class
    Given I have an class with details:
      | ID          | warrior     |
      | Name        | Warrior     |
      | Description | Hits things |
    When I retrieve the class "warrior"
    Then I get an "OK" response
    And I received class:
      | ID          | warrior     |
      | Name        | Warrior     |
      | Description | Hits things |
