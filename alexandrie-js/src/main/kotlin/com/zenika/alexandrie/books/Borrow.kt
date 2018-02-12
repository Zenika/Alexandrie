package com.zenika.alexandrie.books

import com.zenika.alexandrie.books.Borrow.Companion.sendBorrow
import env.environment
import kotlinx.coroutines.experimental.async
import kotlinx.html.ButtonType.reset
import kotlinx.html.InputType.text
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import utils.JsonHttpClient
import utils.usingValue

interface BorrowState : RState {
    var name: String
    var title: String
    var sent: Boolean
}

interface BorrowProps : RProps {
    var borrowFunction: suspend (Book, Borrower) -> Boolean
}

private val defaultProps: BorrowProps.() -> Unit = {
    borrowFunction = { a, b -> sendBorrow(a, b) }
}

class Borrow : RComponent<BorrowProps, BorrowState>() {

    override fun RBuilder.render() {
        form {
            val submit: (Event) -> Unit = { this@Borrow.borrow() }
            attrs {
                onSubmitFunction = submit
            }
            div {
                label {
                    attrs.htmlFor = "name"
                    +"Name"
                }
                input(text) {
                    attrs {
                        id = "name"
                        name = "name"
                        placeholder = "Your Name"
                        onChangeFunction = usingValue {
                            setState {
                                sent = false
                                name = it
                            }
                        }
                    }
                }
            }
            div {
                label {
                    attrs.htmlFor = "book"
                    +"Book title"
                }
                input(text) {
                    attrs {
                        id = "book"
                        name = "book"
                        placeholder = "Book title"
                        onChangeFunction = usingValue {
                            setState {
                                sent = false
                                title = it
                            }
                        }
                    }
                }
            }
            button {
                attrs {
                    onClickFunction = submit
                    type = reset
                }
                +"Borrow"
            }
            if (state.sent) {
                p {
                    +"${state.name} borrowed ${state.title}"
                }
            }
        }
    }

    private fun borrow() {
        async {
            val borrowed = props.borrowFunction(Book(state.title), Borrower(state.name))
            setState {
                sent = borrowed
            }
        }
    }

    companion object {
        suspend fun sendBorrow(book: Book, borrower: Borrower): Boolean {
            JsonHttpClient.put("${environment.backRootUrl}/${book.title}/borrower", borrower)
            return true
        }
    }
}

fun RBuilder.borrow(propsFunction: BorrowProps.() -> Unit = defaultProps) = child(Borrow::class) {
    attrs(propsFunction)
}

