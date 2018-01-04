package utils

import kotlinx.coroutines.experimental.await
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.browser.window
import kotlin.js.Promise
import kotlin.js.json

typealias URL = String

interface JsonFetcher {
    suspend fun <T> fetchJson(method: WindowFetcher.Method, url: URL, body: Any?, parse: (dynamic) -> T): T?
}

object WindowFetcher : JsonFetcher {

    enum class Method {
        GET,
        POST
    }

    interface Fetcher {
        fun fetch(url: URL, init: RequestInit): Promise<Response>
    }

    var fetcher = object : Fetcher {
        override fun fetch(url: URL, init: RequestInit): Promise<Response> = window.fetch(url, init)
    }

    override suspend fun <T> fetchJson(method: Method, url: URL, body: Any?, parse: (dynamic) -> T): T? {
        val response: Response? = fetcher.fetch(url, object : RequestInit {
            override var method: String? = method.name
            override var body: dynamic = body
            override var credentials: RequestCredentials? = "same-origin".asDynamic()
            override var headers: dynamic = json("Accept" to "application/json")
        }).catch {
            null
        }.await()
        return response?.json()?.await()?.let(parse)
    }
}
