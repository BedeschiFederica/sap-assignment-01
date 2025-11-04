Feature: User registration
  As a person,
  I want to create an account
  So that I can login and use the system for delivering packages.

  Scenario: Successful registration
    Given I am on the registration page
    And I have not an account
    When I create an account with a unique username "marco" and a valid password "Secret#123"
    Then I should see a confirmation that my account has been created and receive my identifier

  Scenario: Registration fails with invalid data
    Given I am on the registration page
    And Someone already registered with a username "marco"
    When I register with username "marco" and the password "1234!"
    Then I should see an error "Username already taken"
    And my account should not be created
