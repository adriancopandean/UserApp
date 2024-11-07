Feature: first automation steps

  Scenario: Create api request using project's API classes
    Given I create a valid sign up request using api objects
    When I post request to /register
    Then I validate successful register response
    When I get users list when posting to /users
#    Add validation for get users response

  Scenario: Create api request using Cucumber datatable
    Given I create signup request from datatable
      | username | *GENERATED*    |
      | password | 12345          |
      | email    | *GENERATED*    |
    When I post request named registerRequest to /register
    Then I check response contains the following
      | username | *GENERATED*              |
      | message  | Successfully registered  |
      | email    | *GENERATED*              |

#    add more tests. look inside README.md to see app functionality


