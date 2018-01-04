package index

import app.app
import kotlinext.js.require
import kotlinext.js.requireAll
import react.dom.render
import kotlin.browser.document


const val RESOURCES = "../../../resources/main"


fun main(args: Array<String>) {
    requireAll(require.context("$RESOURCES", true, js("/\\.css$/")))

    document.getElementById("root")?.apply { render(this) { app() } }
}
