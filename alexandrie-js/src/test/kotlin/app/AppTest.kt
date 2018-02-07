package app

import env.environment
import enzyme.shallowElement
import fetch_mock.fetchMock
import org.w3c.fetch.Response
import org.w3c.fetch.ResponseInit
import test.ReactTest
import test.afterServerResponses
import test.assertions.assertContains
import kotlin.test.Test

class AppTest : ReactTest {

    @Test
    fun shouldContainBorrowerName() {
        fetchMock.get("${environment.backRootUrl}/toto/borrower", object {
            val name = "test"
        })

        val element = shallowElement { app() }
        afterServerResponses {
            element.update()
            assertContains(element.text(), "test")
        }
    }

    @Test
    fun shouldSayAbsentIfNoBorrowerFound() {
        fetchMock.get("${environment.backRootUrl}/toto/borrower", NULL_BODY)
        val element = shallowElement { app() }
        afterServerResponses {
            element.update()
            assertContains(element.text(), "Absent")
        }
    }

    val NULL_BODY = Response(null, ResponseInit(headers = object {}))
}
