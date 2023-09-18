@reg
Feature: Message Submission

  Scenario Outline: Message confirmation for user
    Given the user is on home page
    When the user sends a valid message with his "<name>", "<email>", "<phoneNumber>", "<subject>", "<messageDescription>"
    Then the user should be able to see message confirmation with his "<name>" and "<subject>"
    Examples:
      | name  | email         | phoneNumber | subject     | messageDescription                  |
      | david | test@test.com | 12345678910 | testSubject | hi this is test message description |


  Scenario Outline: Message confirmation for user
    Given the user is on home page
    When the user sends a valid message with his "<name>", "<email>", "<phoneNumber>", "<subject>", "<messageDescription>"
    And the user is logged in thru admin panel
    And the user navigates to inbox
    Then the user should be able to see message received from the customer with his "<name>", "<subject>" and "<messageDescription>"
    Examples:
      | name       | email         | phoneNumber | subject     | messageDescription                  |
      | david berg | test@test.com | 12345678910 | testSubject | Hi this is test message description |

  @wip
  Scenario: Invalid Message Submission Error Messages
    Given the user is on home page
    When the user sends a message without a subject
    Then the user should see "Subject" " may not be blank" error messages on the screen
    And the user should see "Subject must be between 5 and 100 characters."


