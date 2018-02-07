package com.zenika.alexandrie.books

class InMemoryBookEvents : BookEventProvider, BookEventRegister {
    private val events: MutableList<BookEvent> = mutableListOf()

    override fun events(): List<BookEvent> = events

    override fun add(bookEvent: BookEvent) {
        this.events.add(bookEvent)
    }

}
