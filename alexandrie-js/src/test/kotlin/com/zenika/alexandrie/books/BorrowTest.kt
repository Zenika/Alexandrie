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
import test.lastPutBody
import kotlin.js.JSON.stringify
import kotlin.test.Test
import kotlin.test.assertEquals

class BorrowTest : ReactTest {

    val NULL_BODY = Response(null, ResponseInit(headers = object {}))

    @Test
    fun shouldSendARequestAskingForBorrowing() {
        fetchMock.put("${environment.backRootUrl}/Clean code/borrower", NULL_BODY)
        val element = shallowElement { borrow() }

        element.setName("Xavier")
        element.setTitle("Clean code")
        element.validate()
        afterServerResponses {
            val body = fetchMock.lastPutBody("${environment.backRootUrl}/Clean code/borrower")
            assertEquals(stringify(Borrower("Xavier")), body)
        }
    }

    private fun ShallowWrapper.setName(name: String) = findInput("name").changeTo(name)

    private fun ShallowWrapper.setTitle(title: String) = findInput("book").changeTo(title)

    private fun ShallowWrapper.validate() = find("button").simulate("click")
}
