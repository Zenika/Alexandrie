package app

import com.zenika.alexandrie.books.borrow
import com.zenika.alexandrie.books.findBorrower
import com.zenika.alexandrie.books.returnBook
import logo.logo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h1
import react.dom.h2
import react.dom.hr


interface AppState : RState {
    var borrower: String
}

class App : RComponent<RProps, AppState>() {

    override fun RBuilder.render() {
        div("App-header") {
            logo()
            h1 {
                +"""Welcome to React with Kotlin"""
            }
        }
        h2 {
            +"Borrow a book"
        }
        borrow()
        hr {}
        h2 {
            +"Find who borrowed a book"
        }
        findBorrower()
        hr {}
        h2 {
            +"Return a book"
        }
        returnBook()
    }

}

fun RBuilder.app() = child(App::class) {}
