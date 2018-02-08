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

interface FindBorrowerComponentState : RState {
    var borrower: Borrower?
    var title: String
    var sent: Boolean
}

class FindBorrowerComponent : RComponent<RProps, FindBorrowerComponentState>() {

    override fun RBuilder.render() {
        form {
            val submit: (Event) -> Unit = { this@FindBorrowerComponent.borrower() }
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
                +"Who borrowed ?"
            }
            if (state.sent) {
                p {
                    +"${state.borrower?.name ?: "Nobody"} borrowed ${state.title}"
                }
            }
        }
    }

    private fun borrower() {
        async {
            val borrower = JsonHttpClient.get("${environment.backRootUrl}/${state.title}/borrower") {
                Borrower(it.borrower.name)
            }
            setState {
                sent = true
                this.borrower = borrower
            }
        }
    }
}

fun RBuilder.findBorrower() = child(FindBorrowerComponent::class) {}

