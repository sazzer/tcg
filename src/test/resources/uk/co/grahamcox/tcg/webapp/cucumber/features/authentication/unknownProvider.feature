Feature: Authentication with an unknown provider

  Scenario: Start authentication with an unknown provider
    When I start authentication with provider "unknown"
    Then I get a "Not Found" response

  Scenario: Handle callback from an unknown provider
    When I receive an authentication callback from provider "unknown" with parameters:
      | state | theState |
      | code  | abc123   |
    Then I get a "Not Found" response
