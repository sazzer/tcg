Feature: Check Access Token

  Scenario: No Access Token provided
    When I check my access token details
    Then I get a "Not Found" response

  Scenario: Valid Access Token provided
    Given I have obtained an Access Token for user "0987654321"
    When I check my access token details
    Then I get an "OK" response