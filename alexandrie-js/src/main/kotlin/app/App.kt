package app

import com.zenika.alexandrie.books.borrow
import com.zenika.alexandrie.books.findBorrower
import logo.logo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.code
import react.dom.div
import react.dom.h1
import react.dom.p
import ticker.ticker


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
        borrow()
        findBorrower()
    }

}

fun RBuilder.app() = child(App::class) {}
