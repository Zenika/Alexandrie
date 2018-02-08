package fetch_mock

import utils.URL
import utils.WindowFetcher.Method
import kotlin.js.Promise

external interface FetchMock {
    fun get(url: URL, response: dynamic): FetchMock
    fun post(url: URL, response: dynamic): FetchMock
    fun put(url: URL, response: dynamic): FetchMock
    fun delete(url: URL, response: dynamic): FetchMock
    fun getOnce(url: URL, response: dynamic): FetchMock
    fun postOnce(url: URL, response: dynamic): FetchMock
    fun putOnce(url: URL, response: dynamic): FetchMock
    fun deleteOnce(url: URL, response: dynamic): FetchMock
    fun restore()
    fun reset()
    fun flush(): Promise<*>
    fun lastCall(filter: URL, method: Method): dynamic
    fun called(filter: URL, method: Method): Boolean
    fun calls(filter: URL, method: Method): dynamic
    fun calls(): dynamic
}

@JsModule("fetch-mock")
external val fetchMock: FetchMock
