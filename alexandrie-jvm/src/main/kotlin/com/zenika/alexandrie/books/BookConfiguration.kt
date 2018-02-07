package com.zenika.alexandrie.books

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BookConfiguration {

    @Bean
    fun inMemoryEvents(): InMemoryBookEvents {
        return InMemoryBookEvents()
    }

    @Bean
    fun borrowerFinder(eventProvider: BookEventProvider): FindBorrower {
        return FindBorrower(eventProvider)
    }
}
