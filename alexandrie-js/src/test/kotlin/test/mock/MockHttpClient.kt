package test.mock

import utils.JsonFetcher
import utils.URL
import utils.WindowFetcher


fun mockHttpClient(vararg responses: Pair<URL, Any?>): JsonFetcher {
    val map: Map<URL, Any?> = mapOf(*responses)
    return object : JsonFetcher {
        suspend override fun <T> fetchJson(method: WindowFetcher.Method, url: URL, body: Any?, parse: (dynamic) -> T): T? = map[url]?.let(parse)
    }
}
