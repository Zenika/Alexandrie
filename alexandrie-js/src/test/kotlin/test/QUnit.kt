package test

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.asPromise
import kotlinx.coroutines.experimental.async

external val QUnit: dynamic

fun qunitTestAsync(block: suspend CoroutineScope.() -> Unit) {
    QUnit.stop()
    async(block = block).asPromise().then({ QUnit.start() }, { QUnit.start() })
}
