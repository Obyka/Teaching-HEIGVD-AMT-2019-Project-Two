Feature: other errors, like send malformed payload for example or malformed headers

  Scenario: get information with malformed token
    Given malformed token
    When I try to get information about me
    Then the system returns me an error with 403 status code

  Scenario: login and get information in header if good format
    Given the credentials for administrator
    When I try to login in the system
    Then the system returns me a token with my identifior and a 200 status code
    Given a valid administrator token
    When I try to get information about me
    Then the system returns me a payload that is a valid payload
