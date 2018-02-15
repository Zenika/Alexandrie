package com.zenika.alexandrie.books

import env.environment
import enzyme.ShallowWrapper
import enzyme.changeTo
import enzyme.findInput
import enzyme.shallowElement
import fetch_mock.fetchMock
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.await
import test.NULL_BODY
import test.ReactTest
import test.assertions.assertContains
import test.assertions.assertNotContains
import test.qunitTestAsync
import utils.WindowFetcher
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReturnBookTest : ReactTest {

    lateinit var deferred: CompletableDeferred<Book>

    val successfulDelete: suspend (Book) -> Boolean = { b ->
        deferred.complete(b)
        true
    }

    val failDelete: suspend (Book) -> Boolean = { b ->
        deferred.complete(b)
        false
    }

    @BeforeTest
    fun createDeferred() {
        deferred = CompletableDeferred()
    }

    @Test
    fun shouldSendARequestReturningBook() {
        fetchMock.deleteOnce("${environment.backRootUrl}/Clean code/borrower", NULL_BODY)
        qunitTestAsync {
            val returned = ReturnBook.doReturn(Book("Clean code"))
            assertTrue(fetchMock.called("${environment.backRootUrl}/Clean code/borrower", WindowFetcher.Method.DELETE), "delete method should be called")
            assertTrue(returned)
            fetchMock.reset()
            fetchMock.restore()
        }
    }


    @Test
    fun shouldSendARequestReturningBookByDefault() {
        fetchMock.deleteOnce("${environment.backRootUrl}/Clean code/borrower", NULL_BODY)
        val element = shallowElement {
            returnBook()
        }

        element.setTitle("Clean code")
        element.validate()
        qunitTestAsync {
            fetchMock.flush().await()
            assertTrue(fetchMock.called("${environment.backRootUrl}/Clean code/borrower", WindowFetcher.Method.DELETE), "delete method should be called")
            fetchMock.reset()
            fetchMock.restore()
        }
    }

    @Test
    fun shouldCallMethodWithReturnedBook() {
        val element = shallowElement {
            returnBook {
                returnFunction = successfulDelete
            }
        }

        element.setTitle("Clean code")
        element.validate()
        qunitTestAsync {
            val book = deferred.await()
            assertEquals(Book("Clean code"), book)
        }
    }

    @Test
    fun shouldPrintThatTheBookHasBeenReturned() {
        val element = shallowElement {
            returnBook {
                returnFunction = successfulDelete
            }
        }

        element.setTitle("Clean code")
        element.validate()
        qunitTestAsync {
            deferred.await()
            element.update()
            assertContains(element.text(), "Clean code returned")
        }
    }

    @Test
    fun shouldNotConfirmReturnedWhenFails() {
        val element = shallowElement {
            returnBook {
                returnFunction = failDelete
            }
        }

        element.setTitle("Clean code")
        element.validate()
        qunitTestAsync {
            deferred.await()
            element.update()
            assertNotContains(element.text(), "Clean code returned")
        }
    }

    private fun ShallowWrapper.setTitle(title: String) = findInput("book").changeTo(title)

    private fun ShallowWrapper.validate() = find("button").simulate("click")
}
