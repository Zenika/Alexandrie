package com.zenika.alexandrie

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@SpringBootApplication
class AlexandrieApp

fun main(args: Array<String>) {
    SpringApplication.run(AlexandrieApp::class.java, *args)
}

@EnableWebSecurity
class WebSecurityConfig : WebMvcConfigurerAdapter() {

    protected fun configure(http: HttpSecurity) {
        http.cors().and()
                .authorizeRequests()
                .anyRequest().anonymous()
    }
}
