package fetch_mock

import kotlin.js.Promise

external interface FetchMock {
    fun get(url: String, response: dynamic): FetchMock
    fun post(url: String, response: dynamic): FetchMock
    fun put(url: String, response: dynamic): FetchMock
    fun delete(url: String, response: dynamic): FetchMock
    fun restore()
    fun reset()
    fun flush(): Promise<*>
    fun lastCall(filter: String, method: String): dynamic
    fun calls(filter: String, method: String): dynamic
    fun calls(): dynamic
}

@JsModule("fetch-mock")
external val fetchMock: FetchMock
