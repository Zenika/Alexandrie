package com.zenika.alexandrie.books.fake

import com.zenika.alexandrie.books.BookEvent
import com.zenika.alexandrie.books.BookEventProvider

class InMemoryBookEventProvider : BookEventProvider {

    private val events: MutableList<BookEvent> = mutableListOf()

    override fun events(): List<BookEvent> = events

    fun add(vararg events: BookEvent) {
        events.forEach { this.events.add(it) }
    }
}
