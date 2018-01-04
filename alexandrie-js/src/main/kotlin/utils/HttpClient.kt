package utils

import utils.WindowFetcher.Method.GET
import utils.WindowFetcher.Method.POST
import kotlin.js.JSON.stringify


interface HttpClient {
    suspend fun <T> get(url: String, body: Any? = null, parse: (dynamic) -> T): T?
    suspend fun <T> post(url: String, body: Any, parse: (dynamic) -> T): T?
}

object JsonHttpClient : HttpClient {

    var fetcher: JsonFetcher = WindowFetcher

    override suspend fun <T> get(url: URL, body: Any?, parse: (dynamic) -> T): T? = fetcher.fetchJson(GET, url, body, parse)

    override suspend fun <T> post(url: URL, body: Any, parse: (dynamic) -> T): T? = fetcher.fetchJson(POST, url, stringify(body), parse)

}

