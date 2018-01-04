package com.zenika.alexandrie.steps

import com.zenika.alexandrie.BorrowEvent
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.assertj.core.api.Assertions


class Steps {

    lateinit var borrowEvent: BorrowEvent
    lateinit var borrowerName: String

    @Given("""^that "([^"]*)" borrowed the book "([^"]*)"$""")
    fun `borrowed the book`(userName: String, bookName: String) {
        borrowEvent = BorrowEvent(userName, bookName)
    }

    @When("""^we ask the system who has the book$""")
    fun `we ask the system who has the book`() {
        borrowerName = borrowEvent.userName
    }

    @Then("""^it should answer "([^"]*)"$""")
    fun `it should answer`(answer: String) {
        Assertions.assertThat(borrowerName).isEqualTo(answer)
    }
}
