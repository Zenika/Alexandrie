package com.zenika.alexandrie.books

import env.environment
import enzyme.ShallowWrapper
import enzyme.changeTo
import enzyme.findInput
import enzyme.shallowElement
import fetch_mock.fetchMock
import org.w3c.fetch.Response
import org.w3c.fetch.ResponseInit
import test.ReactTest
import test.afterServerResponses
import test.assertions.assertContains
import kotlin.test.Test

class FindBorrowerTest : ReactTest {

    val NULL_BODY = Response(null, ResponseInit(headers = object {}))

    fun hatoesResponse(pair: Pair<String, Any?>) {
        val result = object {
            val links = Array(0, { "" })
        }.asDynamic()
        result[pair.first] = pair.second
        return result
    }

    @Test
    fun shouldPrintBorrower() {
        fetchMock.get("${environment.backRootUrl}/Clean code/borrower", hatoesResponse("borrower" to Borrower("Xavier")))
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
        fetchMock.get("${environment.backRootUrl}/Clean code/borrower", NULL_BODY)
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
