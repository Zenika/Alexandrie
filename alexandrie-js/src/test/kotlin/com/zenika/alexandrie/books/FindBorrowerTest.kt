package com.zenika.alexandrie.books

import env.environment
import enzyme.ShallowWrapper
import enzyme.changeTo
import enzyme.findInput
import enzyme.shallowElement
import fetch_mock.fetchMock
import test.ReactTest
import test.assertions.assertContains
import test.hateoasResponse
import test.qunitTestAsync
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FindBorrowerTest : ReactTest {

    @Test
    fun shouldGetBorrower() {
        fetchMock.getOnce("${environment.backRootUrl}/Clean code/borrower", hateoasResponse("borrower" to Borrower("Xavier")))

        qunitTestAsync {
            val borrower = FindBorrowerComponent.findBorrowerOf(Book("Clean code"))
            assertEquals(Borrower("Xavier"), borrower)
            fetchMock.reset()
            fetchMock.restore()
        }
    }

    @Test
    fun shouldKeepNull() {
        fetchMock.getOnce("${environment.backRootUrl}/Clean code/borrower", hateoasResponse("borrower" to null))

        qunitTestAsync {
            val borrower = FindBorrowerComponent.findBorrowerOf(Book("Clean code"))
            assertNull(borrower)
            fetchMock.reset()
            fetchMock.restore()
        }
    }

    @Test
    fun shouldPrintBorrower() {
        val element = shallowElement {
            findBorrower {
                findBorrowerFunction = { Borrower("Xavier") }
            }
        }

        element.setTitle("Clean code")
        element.validate()

        qunitTestAsync {
            element.update()
            assertContains(element.text(), "Xavier borrowed Clean code")
        }
    }

    @Test
    fun shouldSayNobodyBorrowedBook() {
        val element = shallowElement {
            findBorrower {
                findBorrowerFunction = { null }
            }
        }

        element.setTitle("Clean code")
        element.validate()

        qunitTestAsync {
            element.update()
            assertContains(element.text(), "Nobody borrowed Clean code")
        }
    }

    private fun ShallowWrapper.setTitle(title: String) = findInput("book").changeTo(title)

    private fun ShallowWrapper.validate() = find("button").simulate("click")
}
