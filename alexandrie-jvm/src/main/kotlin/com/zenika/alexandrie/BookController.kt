package com.zenika.alexandrie

import org.springframework.hateoas.ResourceSupport
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController {

    @CrossOrigin(origins = ["http://localhost:8088"])
    @RequestMapping("/{book}/borrower")
    fun toto(@PathVariable book: String): Borrower {
        return Borrower("Bob")
    }

}

data class Borrower(val name: String) : ResourceSupport()
