package com.zenika.alexandrie.books

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

interface ReturnBookState : RState {
    var title: String
    var sent: Boolean
}

class ReturnBook : RComponent<RProps, FindBorrowerComponentState>() {

    override fun RBuilder.render() {
        form {
            val submit: (Event) -> Unit = { this@ReturnBook.returnBook() }
            attrs {
                onSubmitFunction = submit
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
                +"Return book"
            }
            if (state.sent) {
                p {
                    +"${state.title} returned"
                }
            }
        }
    }

    private fun returnBook() {
        async {
            JsonHttpClient.delete("${environment.backRootUrl}/${state.title}/borrower")
            setState {
                sent = true
            }
        }
    }
}

fun RBuilder.returnBook() = child(ReturnBook::class) {}

