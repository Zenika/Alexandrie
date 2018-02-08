package com.zenika.alexandrie.books

import env.environment
import enzyme.ShallowWrapper
import enzyme.changeTo
import enzyme.findInput
import enzyme.shallowElement
import fetch_mock.fetchMock
import test.NULL_BODY
import test.ReactTest
import test.afterServerResponses
import utils.WindowFetcher
import kotlin.test.Test
import kotlin.test.assertTrue

class ReturnBookTest : ReactTest {

    @Test
    fun shouldSendARequestReturningBook() {
        fetchMock.deleteOnce("${environment.backRootUrl}/Clean code/borrower", NULL_BODY)
        val element = shallowElement { returnBook() }

        element.setTitle("Clean code")
        element.validate()
        afterServerResponses {
            assertTrue(fetchMock.called("${environment.backRootUrl}/Clean code/borrower", WindowFetcher.Method.DELETE),"delete method should be called")
        }
    }

    private fun ShallowWrapper.setTitle(title: String) = findInput("book").changeTo(title)

    private fun ShallowWrapper.validate() = find("button").simulate("click")
}
