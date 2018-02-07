package com.zenika.alexandrie.books

sealed class BookEvent(open val book: Book)

data class BookBorrowed(val borrower: Borrower, override val book: Book) : BookEvent(book)

data class BookReturned(override val book: Book) : BookEvent(book)

interface BookEventProvider {
    fun events() : List<BookEvent>
}

interface BookEventRegister {
    fun add(bookEvent: BookEvent)
}
