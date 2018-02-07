package utils

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.asPromise
import kotlinx.coroutines.experimental.async
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import test.qunitTestAsync
import utils.WindowFetcher.Method.GET
import kotlin.js.Promise
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class WindowHttpClientTest {

    @Test
    fun resultIsNullWhenRequestFails() {
        qunitTestAsync {
            WindowFetcher.fetcher = failRequests()
            val result = WindowFetcher.fetchJson(GET, "http://whatever", noParams) {}
            assertNull(result)
        }
    }

    @Test
    fun resultIsParsedWhenRequestSucced() {
        qunitTestAsync {
            WindowFetcher.fetcher = resolveJsonRequests(object {
                val value = "test"
            })
            val result = WindowFetcher.fetchJson(GET, "http://whatever", noParams) {
                object {
                    val parsedValue = it.value
                }
            }
            assertNotNull(result)
            assertEquals("test", result?.parsedValue)
        }
    }

    @Test
    fun requestParametersShouldBeTransmitted() {
        qunitTestAsync {
            val spyedRequests = spyedRequests()
            WindowFetcher.fetcher = spyedRequests
            WindowFetcher.fetchJson(GET, "http://whatever", noParams) {}
            assertNotNull(spyedRequests.url)
            assertNotNull(spyedRequests.init)
            assertEquals("http://whatever", spyedRequests.url)
            assertEquals("GET", spyedRequests.init?.method)
        }
    }

    private val noParams = object {}

    private fun resolveJsonRequests(result: Any): WindowFetcher.Fetcher {
        return object : WindowFetcher.Fetcher {
            override fun fetch(url: URL, init: RequestInit): Promise<Response> = Promise.resolve(Response(body = JSON.stringify(result)))
        }
    }

    private fun failRequests(): WindowFetcher.Fetcher {
        return object : WindowFetcher.Fetcher {
            override fun fetch(url: URL, init: RequestInit): Promise<Response> = Promise.reject(Exception())
        }
    }

    private fun spyedRequests(): SpyedRequest {
        return object : SpyedRequest {
            override var url: URL? = null
            override var init: RequestInit? = null

            override fun fetch(url: URL, init: RequestInit): Promise<Response> {
                this.url = url
                this.init = init
                return Promise.reject(Exception())
            }
        }
    }

    interface SpyedRequest : WindowFetcher.Fetcher {
        var url: URL?
        var init: RequestInit?
    }
}
