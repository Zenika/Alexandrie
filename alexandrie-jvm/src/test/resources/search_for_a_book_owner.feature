Feature: Search for a book owner

  Any one at Zenika should be allowed to borrow a book.
  Nevertheless, we want all Zenika to know who as a given book so that we can negotiate.

  How did I ask for that book specifically ?
  How did the book were entered in the system ?

  Scenario: "Bob" has the book "Clean code"
    Given that "Bob" borrowed the book "Clean code"
    When we ask the system who has the book
    Then it should answer "Bob"

  Scenario: "Bob" has returned the book "Clean code"
    Given that "Bob" borrowed the book "Clean code"
    And "Bob" returned the book "Clean code"
    When we ask the system who has the book
    Then it should answer nobody
