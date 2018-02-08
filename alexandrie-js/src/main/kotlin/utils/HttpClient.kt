package utils

import utils.WindowFetcher.Method.*


interface HttpClient {
    suspend fun <T> get(url: URL, body: Any? = null, parse: (dynamic) -> T): T?
    suspend fun post(url: URL, body: Any)
    suspend fun put(url: URL, body: Any)
}

object JsonHttpClient : HttpClient {

    var fetcher: JsonFetcher = WindowFetcher

    override suspend fun <T> get(url: URL, body: Any?, parse: (dynamic) -> T): T? = fetcher.fetchJson(GET, url, body, parse)

    override suspend fun post(url: URL, body: Any) {
        fetcher.sendJson(POST, url, body)
    }

    override suspend fun put(url: URL, body: Any) {
        fetcher.sendJson(PUT, url, body)
    }

}

