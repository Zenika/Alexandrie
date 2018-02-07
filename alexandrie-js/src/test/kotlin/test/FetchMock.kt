package test

import fetch_mock.FetchMock
import fetch_mock.fetchMock
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.delay

fun FetchMock.lastCallBody(filter: String, method: String): String = lastCall(filter, method)[1].body as String

fun FetchMock.lastPutBody(filter: String): String = lastCallBody(filter, "PUT")

fun afterServerResponses(block: suspend CoroutineScope.() -> Unit) {
    qunitTestAsync {
        fetchMock.flush().await()
        delay(100)
        block()
        fetchMock.reset()
        fetchMock.restore()
    }
}
