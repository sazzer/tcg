Feature: Authentication against Google

  Scenario: Start authentication with Google
    When I start authentication with provider "google"
    Then I get a "Found" response
    And I get a redirect to URL "http://localhost/api/authentication/fake/google/auth" with parameters:
      | client_id     | myGoogleClientId                                                  |
      | response_type | code                                                              |
      | scope         | openid%20email                                                    |
      | redirect_uri  | http://localhost:${serverPort}/api/authentication/google/redirect |

  Scenario: Handle callback after authentication of a new user
    Given I expect a request to Google to exchange Authorization Code "abc123" for Access Token "thisIsMyAccessToken"
    And I expect a request to Google for the current user of Access Token "thisIsMyAccessToken":
      | User ID | 1234567890             |
      | Name    | Graham Cox             |
      | Email   | graham@grahamcox.co.uk |
    When I receive an authentication callback from provider "google" with parameters:
      | State | theState |
      | Code  | abc123   |
    Then I get an "OK" response
    And I get an Access Token response
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
      | State | theState |
      | Code  | abc123   |
    Then I get an "OK" response
    And the database contains a user for this User ID with details:
      | User ID            | 0987654321       |
      | Name               | Test User        |
      | Email              | test@example.com |
      | Google Provider ID | 1234567890       |

  @UI
  Scenario: Log in via Google
    Given I expect a request to Google to exchange Authorization Code "thisIsMyAuthorizationCode" for Access Token "thisIsMyAccessToken"
    And I expect a request to Google for the current user of Access Token "thisIsMyAccessToken":
      | User ID | 1234567890             |
      | Name    | Graham Cox             |
      | Email   | graham@grahamcox.co.uk |
    And I have loaded the home page
    When I log in using Google
    Then the Login menu is not present
    And the User menu is present
    And the current user is "Graham Cox"
