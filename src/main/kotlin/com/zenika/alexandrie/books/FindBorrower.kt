package com.zenika.alexandrie.books

class FindBorrower(private val eventProvider: BookEventProvider) {
    operator fun invoke(book: Book) = eventProvider.events()
            .lastOrNull { it.book == book }
            .let { it as? BookBorrowed }
            ?.borrower

}
