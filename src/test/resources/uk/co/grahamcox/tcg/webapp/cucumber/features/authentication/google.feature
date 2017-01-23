@wip
Feature: Authentication against Google

  Scenario: Start authentication with Google
    When I start authentication with provider "google"
    Then I get a "Found" response
    And I get a redirect to URL "https://accounts.google.com/o/oauth2/v2/auth" with parameters:
      | client_id     | myGoogleClientId                                                  |
      | response_type | code                                                              |
      | scope         | openid email                                                      |
      | redirect_uri  | http://localhost:${serverPort}/api/authentication/google/redirect |

  Scenario: Handle callback after authentication of a new user
    Given I expect a request to Google to exchange Authorization Code "abc123" for Access Token "thisIsMyAccessToken"
    And I expect a request to Google for the current user of Access Token "thisIsMyAccessToken":
      | User ID | 1234567890             |
      | Name    | Graham Cox             |
      | Email   | graham@grahamcox.co.uk |
    When I receive an authentication callback from provider "google" with parameters:
      | state | theState |
      | code  | abc123   |
    Then I get an "OK" response
    And the database contains a user for this User ID with details:
      | Name               | Graham Cox             |
      | Email              | graham@grahamcox.co.uk |
      | Google Provider ID | 1234567890             |

  Scenario: Handle callback after authentication of a known user
    Given I expect a request to Google to exchange Authorization Code "abc123" for Access Token "thisIsMyAccessToken"
    And I expect a request to Google for the current user of Access Token "thisIsMyAccessToken":
      | User ID | 1234567890             |
      | Name    | Graham Cox             |
      | Email   | graham@grahamcox.co.uk |
    And I have a User with details:
      | User ID            | 0987654321       |
      | Name               | Test User        |
      | Email              | test@example.com |
      | Google Provider ID | 1234567890       |
    When I receive an authentication callback from provider "google" with parameters:
      | state | theState |
      | code  | abc123   |
    Then I get an "OK" response
    And the database contains a user for this User ID with details:
      | User ID            | 0987654321       |
      | Name               | Test User        |
      | Email              | test@example.com |
      | Google Provider ID | 1234567890       |


