package com.zenika.alexandrie.steps

import com.zenika.alexandrie.books.*
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.assertj.core.api.Assertions


class Steps {

    val inMemoryBookEvents: InMemoryBookEvents = InMemoryBookEvents()
    val findBorrower = FindBorrower(inMemoryBookEvents)
    lateinit var bookTitle: String
    var borrowerName: String? = null

    @Given("""^that "([^"]*)" borrowed the book "([^"]*)"$""")
    fun `borrowed the book`(userName: String, bookTitle: String) {
        this.bookTitle = bookTitle
        inMemoryBookEvents.add(BookBorrowed(Borrower(userName), Book(bookTitle)))
    }

    @When("""^we ask the system who has the book$""")
    fun `we ask the system who has the book`() {
        borrowerName = findBorrower(Book(bookTitle))?.name
    }

    @Then("""^it should answer "([^"]*)"$""")
    fun `it should answer`(answer: String) {
        Assertions.assertThat(borrowerName).isEqualTo(answer)
    }


    @And("""^"([^"]*)" returned the book "([^"]*)"$""")
    fun `returned the book`(userName: String, bookName: String) {
        inMemoryBookEvents.add(BookReturned(Book(bookName)))
    }

    @Then("""^it should answer nobody$""")
    fun `it should answer nobody`() {
        Assertions.assertThat(borrowerName).isNull()
    }
}
