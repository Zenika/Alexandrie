package app

import env.environment
import enzyme.shallowElement
import test.ReactTest
import test.assertions.assertContains
import test.mock.mockHttpClient
import utils.JsonHttpClient
import kotlin.test.Test

class AppTest : ReactTest {

    @Test
    fun shouldContainBorrowerName() {
        JsonHttpClient.fetcher = mockHttpClient(
                "${environment.backRootUrl}/toto/borrower" to object {
                    val name = "test"
                }
        )
        val element = shallowElement { app() }
        assertContains(element.text(), "test")
    }

    @Test
    fun shouldSayAbsentIfNoBorrowerFound() {
        JsonHttpClient.fetcher = mockHttpClient(
                "${environment.backRootUrl}/toto/borrower" to null
        )
        val element = shallowElement { app() }
        assertContains(element.text(), "Absent")
    }
}
