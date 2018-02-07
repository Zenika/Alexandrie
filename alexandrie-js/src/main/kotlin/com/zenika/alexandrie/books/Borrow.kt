package com.zenika.alexandrie.books

import env.environment
import kotlinx.coroutines.experimental.CoroutineStart
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

class Borrow : RComponent<RProps, BorrowState>() {

    override fun RBuilder.render() {
        form {
            val submit: (Event) -> Unit = { this@Borrow.borrow() }
            attrs {
                onSubmitFunction = submit
            }
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
        async(start = CoroutineStart.UNDISPATCHED) {
            JsonHttpClient.put("${environment.backRootUrl}/${state.title}/borrower", Borrower(state.name))
            setState {
                sent = true
            }
        }
    }
}

fun RBuilder.borrow() = child(Borrow::class) {}

