package com.zenika.alexandrie.books

import org.springframework.hateoas.ResourceSupport
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*

@RestController
class BookController(private val findBorrower: FindBorrower,
                     private val bookEventRegister: BookEventRegister) {

    @CrossOrigin(origins = ["http://localhost:8088"])
    @RequestMapping("/{title}/borrower", method = [GET])
    fun borrower(@PathVariable title: String): ResourceBorrower? {
        return findBorrower(Book(title))?.let { ResourceBorrower(it) }
    }

    @CrossOrigin(origins = ["http://localhost:8088"])
    @RequestMapping("/{title}/borrower", method = [PUT])
    fun borrow(@PathVariable title: String, @RequestBody borrower: Borrower) {
        bookEventRegister.add(BookBorrowed(borrower, Book(title)))
    }


    @CrossOrigin(origins = ["http://localhost:8088"])
    @RequestMapping("/{title}/borrower", method = [DELETE])
    fun returnBook(@PathVariable title: String) {
        bookEventRegister.add(BookReturned(Book(title)))
    }

}

data class ResourceBorrower(val borrower: Borrower) : ResourceSupport()
