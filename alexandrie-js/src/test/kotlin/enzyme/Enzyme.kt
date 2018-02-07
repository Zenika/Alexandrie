@file:JsModule("enzyme")

package enzyme

import react.ReactElement

external fun shallow(component: ReactElement?): ShallowWrapper

external fun mount(component: ReactElement?): ReactWrapper

external fun configure(adapter: dynamic)


external class ShallowWrapper {
    fun simulate(event: String, vararg args: dynamic): ShallowWrapper
    fun find(selector: String): ShallowWrapper
    fun contains(element: ReactElement?): Boolean
    fun contains(element: Array<ReactElement>): Boolean
    fun text(): String
    fun html(): String
    fun debug(): String
    val length: Int
    fun update(): ShallowWrapper
}

external class ReactWrapper {
    fun find(selector: String): ReactWrapper
    fun contains(element: ReactElement?): Boolean
    fun contains(element: Array<ReactElement>): Boolean
    fun text(): String
    fun html(): String
    fun debug(): String
    fun at(index: Int): ReactWrapper
    fun update(): Unit
    val length: Int
}
