package com.zenika.alexandrie.books

import com.zenika.alexandrie.books.fake.InMemoryBookEventProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class FindBorrowerShould {

    val bookEventProvider: InMemoryBookEventProvider = InMemoryBookEventProvider()

    @Test
    fun findNobodyWhenBookDoesntExists() {
        val findBorrower = FindBorrower(bookEventProvider)
        assertNull(findBorrower(Book("Clean code")))
    }

    @Test
    fun findLatestBorrowerOfTheBook() {
        val findBorrower = FindBorrower(bookEventProvider)
        bookEventProvider.add(BookBorrowed(Borrower("Bob"), Book("Clean code")))
        assertEquals(findBorrower(Book("Clean code")), Borrower("Bob"))
    }

    @Test
    fun findNobodyWhenBookHasBeenReturned() {
        val findBorrower = FindBorrower(bookEventProvider)
        bookEventProvider.add(
                BookBorrowed(Borrower("Bob"), Book("Clean code")),
                BookReturned(Book("Clean code"))
        )
        assertNull(findBorrower(Book("Clean code")))
    }

}
