package app

import env.environment
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.async
import logo.logo
import react.*
import react.dom.code
import react.dom.div
import react.dom.h1
import react.dom.p
import ticker.ticker
import utils.JsonHttpClient


interface AppState : RState {
    var borrower: String
}

class App : RComponent<RProps, AppState>() {

    override fun RBuilder.render() {
        div("App-header") {
            logo()
            h1 {
                +"Welcome to React with Kotlin"
            }
        }
        p("App-intro") {
            +"To get started, edit "
            code { +"app/App.kt" }
            +" and save to reload."
        }
        p("App-ticker") {
            ticker()
        }
        p {
            +"Borrower is : ${state.borrower}"
        }
    }

    override fun componentDidMount() {
        async(start = CoroutineStart.UNDISPATCHED) {
            val name: String? = JsonHttpClient.get("${environment.backRootUrl}/toto/borrower") {
                it.name as String
            }
            setState {
                borrower = name ?: "Absent"
            }
        }
    }

}

fun RBuilder.app() = child(App::class) {}
