Feature: Google Authentication
  Test the various parts of authentication against the Google+ Service

  Scenario: Generate redirect to authenticate against Google
    When I initiate authentication against "google"
    Then I get a redirect to "https://accounts.google.com/o/oauth2/v2/auth" with parameters:
    | client_id     | myGoogleClientId                                         |
    | response_type | code                                                     |
    | scope         | openid%20email                                           |
    | redirect_uri  | http://localhost:8080/api/authentication/google/redirect |