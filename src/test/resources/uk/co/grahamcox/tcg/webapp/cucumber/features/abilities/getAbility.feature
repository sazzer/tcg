Feature: Get an Ability

  Scenario: Retrieve an unknown Ability
    When I retrieve the ability "unknown"
    Then I get a "Not Found" response

  Scenario: Retrieve a known Ability
    Given I have an ability with details:
      | ID          | powerstrike       |
      | Name        | Power Strike      |
      | Description | Hit things harder |
    When I retrieve the ability "powerstrike"
    Then I get an "OK" response
    And I received ability:
      | ID          | powerstrike       |
      | Name        | Power Strike      |
      | Description | Hit things harder |
