package utils

import kotlinx.html.INPUT
import org.w3c.dom.events.Event

fun INPUT.usingValue(f: (value: String) -> Unit): (Event) -> Unit {
    return { e ->
            f(e.target.asDynamic().value)
    }
}
