package utils

import kotlinx.coroutines.experimental.await
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.browser.window
import kotlin.js.JSON.stringify
import kotlin.js.Promise
import kotlin.js.json

typealias URL = String

interface JsonFetcher {
    suspend fun <T> fetchJson(method: WindowFetcher.Method, url: URL, body: Any?, parse: (dynamic) -> T): T?

    suspend fun sendJson(method: WindowFetcher.Method, url: URL, body: Any?): Response?
}

object WindowFetcher : JsonFetcher {
    enum class Method {
        GET,
        POST,
        PUT,
        DELETE
    }

    interface Fetcher {

        fun fetch(url: URL, init: RequestInit): Promise<Response>
    }

    var fetcher = object : Fetcher {
        override fun fetch(url: URL, init: RequestInit): Promise<Response> = window.fetch(url, init)
    }

    suspend override fun sendJson(method: Method, url: URL, body: Any?): Response? {
        return fetcher.fetch(url, object : RequestInit {
            override var method: String? = method.name
            override var body: dynamic = stringify(body)
            override var credentials: RequestCredentials? = "same-origin".asDynamic()
            override var headers: dynamic = json(
                    "Accept" to "application/json",
                    "Content-type" to "application/json")
        }).catch {
            null
        }.await()
    }

    override suspend fun <T> fetchJson(method: Method, url: URL, body: Any?, parse: (dynamic) -> T): T? {
        val response: Response? = fetcher.fetch(url, object : RequestInit {
            override var method: String? = method.name
            override var body: dynamic = body?.let { stringify(it) }
            override var credentials: RequestCredentials? = "same-origin".asDynamic()
            override var headers: dynamic = json(
                    "Accept" to "application/json",
                    "Content-type" to "application/json")
        }).catch {
            return@catch null
        }.await()
        return response?.json()?.await()?.let(parse)
    }
}
