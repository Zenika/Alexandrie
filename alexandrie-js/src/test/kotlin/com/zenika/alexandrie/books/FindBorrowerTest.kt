package com.zenika.alexandrie.books

import env.environment
import enzyme.ShallowWrapper
import enzyme.changeTo
import enzyme.findInput
import enzyme.shallowElement
import fetch_mock.fetchMock
import test.ReactTest
import test.afterServerResponses
import test.assertions.assertContains
import test.hateoasResponse
import kotlin.test.Test

class FindBorrowerTest : ReactTest {

    @Test
    fun shouldPrintBorrower() {
        fetchMock.getOnce("${environment.backRootUrl}/Clean code/borrower", hateoasResponse("borrower" to Borrower("Xavier")))
        val element = shallowElement { findBorrower() }

        element.setTitle("Clean code")
        element.validate()

        afterServerResponses {
            element.update()
            assertContains(element.text(), "Xavier borrowed Clean code")
        }
    }

    @Test
    fun shouldSayNobodyBorrowedBook() {
        fetchMock.getOnce("${environment.backRootUrl}/Clean code/borrower", hateoasResponse("borrower" to null))
        val element = shallowElement { findBorrower() }

        element.setTitle("Clean code")
        element.validate()

        afterServerResponses {
            element.update()
            assertContains(element.text(), "Nobody borrowed Clean code")
        }
    }

    private fun ShallowWrapper.setTitle(title: String) = findInput("book").changeTo(title)

    private fun ShallowWrapper.validate() = find("button").simulate("click")
}
