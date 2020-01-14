Feature: testing error status code for every API operations

  Scenario: login with bad credentials
    Given bad credentials
    When I try to connect with bad credentials
    Then the system returns me an error that it is a bad login with 401 status code