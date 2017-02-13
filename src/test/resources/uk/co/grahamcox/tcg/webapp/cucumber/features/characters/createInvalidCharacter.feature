Feature: Character Creation with Invalid Inputs

  Background:
    Given I have an race with details:
      | ID          | human                    |
      | Name        | Human                    |
      | Description | Pretty much standard     |
    And I have an race with details:
      | ID          | dwarf                    |
      | Name        | Dwarf                    |
      | Description | Really just much shorter |
    And I have an gender with details:
      | ID          | male                     |
      | Name        | Male                     |
      | Description | Manly                    |
      | Race        | human                    |
    And I have an class with details:
      | ID          | warrior                  |
      | Name        | Warrior                  |
      | Description | Hits things              |
    And I have a User with details:
      | User ID            | 0987654321        |
      | Name               | Test User         |
      | Email              | test@example.com  |
      | Google Provider ID | 1234567890        |
    And I have obtained an Access Token for user "0987654321"

  Scenario Outline: Create Character with invalid information: <Comment>
    When I create a Character with details:
      | Name   | Bob      |
      | Race   | <Race>   |
      | Gender | <Gender> |
      | Class  | <Class>  |
    Then I get an "Bad Request" response
    And I get an error response of:
      | Error Code | INVALID_FIELDS |
      | Fields     | <Error Fields> |

    Examples:
      | Race    | Gender  | Class   | Error Fields    | Comment                 |
      | unknown | male    | warrior | race: unknown   | Invalid Race            |
      | human   | unknown | warrior | gender: unknown | Invalid Gender          |
      | human   | male    | unknown | class: unknown  | Invalid Class           |
      | dwarf   | male    | warrior | gender: male    | Invalid Gender for Race |