package com.zenika.alexandrie.books

import env.environment
import enzyme.ShallowWrapper
import enzyme.changeTo
import enzyme.findInput
import enzyme.shallowElement
import fetch_mock.fetchMock
import kotlinx.coroutines.experimental.CompletableDeferred
import test.NULL_BODY
import test.ReactTest
import test.assertions.assertContains
import test.assertions.assertNotContains
import test.lastPutBody
import test.qunitTestAsync
import kotlin.js.JSON.stringify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BorrowTest : ReactTest {

    lateinit var deferred: CompletableDeferred<Pair<Book, Borrower>>

    val successfulSend: suspend (Book, Borrower) -> Boolean = { book, borrower ->
        deferred.complete(Pair(book, borrower))
        true
    }

    val failingSend: suspend (Book, Borrower) -> Boolean = { book, borrower ->
        deferred.complete(Pair(book, borrower))
        false
    }

    @BeforeTest
    fun createDeferred() {
        deferred = CompletableDeferred()
    }

    @Test
    fun shouldSendARequestAskingForBorrowing() {
        fetchMock.putOnce("${environment.backRootUrl}/Clean code/borrower", NULL_BODY)
        qunitTestAsync {
            val borrowed = Borrow.sendBorrow(Book("Clean code"), Borrower("Xavier"))
            val body = fetchMock.lastPutBody("${environment.backRootUrl}/Clean code/borrower")
            assertEquals(stringify(Borrower("Xavier")), body)
            assertTrue(borrowed, "should confirm borrowing on success")
        }
    }

    @Test
    fun shouldCallGivenFunctionWithCorrectValues() {
        val element = shallowElement {
            borrow {
                borrowFunction = successfulSend
            }
        }

        element.setName("Xavier")
        element.setTitle("Clean code")
        element.validate()

        qunitTestAsync {
            val (book, borrower) = deferred.await()
            assertEquals(Book("Clean code"), book)
            assertEquals(Borrower("Xavier"), borrower)
        }
    }

    @Test
    fun shouldConfirmBorrowing() {
        val element = shallowElement {
            borrow {
                borrowFunction = successfulSend
            }
        }
        element.setName("Xavier")
        element.setTitle("Clean code")
        element.validate()

        qunitTestAsync {
            deferred.await()
            element.update()
            assertContains(element.text(), "Xavier borrowed Clean code")
        }
    }

    @Test
    fun shouldNotConfirmBorrowingWhenFail() {
        val element = shallowElement {
            borrow {
                borrowFunction = failingSend
            }
        }

        element.setName("Xavier")
        element.setTitle("Clean code")
        element.validate()

        qunitTestAsync {
            deferred.await()
            element.update()
            assertNotContains(element.text(), "Xavier borrowed Clean code")
        }
    }

    private fun ShallowWrapper.setName(name: String) = findInput("name").changeTo(name)

    private fun ShallowWrapper.setTitle(title: String) = findInput("book").changeTo(title)

    private fun ShallowWrapper.validate() = find("button").simulate("click")
}
