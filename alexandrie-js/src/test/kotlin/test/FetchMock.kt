package test

import fetch_mock.FetchMock
import fetch_mock.fetchMock
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.delay
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import org.w3c.fetch.ResponseInit
import utils.WindowFetcher.Method
import utils.WindowFetcher.Method.PUT

fun FetchMock.lastCallRequest(filter: String, method: Method): RequestInit = lastCall(filter, method)[1]

fun FetchMock.lastCallBody(filter: String, method: Method): String = lastCallRequest(filter, method).body

fun FetchMock.lastPutBody(filter: String): String = lastCallBody(filter, PUT)

fun afterServerResponses(block: suspend CoroutineScope.() -> Unit) {
    qunitTestAsync {
        fetchMock.flush().await()
        delay(100)
        block()
        fetchMock.reset()
        fetchMock.restore()
    }
}

val NULL_BODY = Response(null, ResponseInit(headers = object {}))

fun hateoasResponse(pair: Pair<String, Any?>) {
    val result = object {
        val links = emptyArray<String>()
    }.asDynamic()
    result[pair.first] = pair.second
    return result
}
