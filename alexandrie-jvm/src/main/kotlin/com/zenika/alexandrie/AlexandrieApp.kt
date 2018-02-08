package com.zenika.alexandrie

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@SpringBootApplication
class AlexandrieApp

fun main(args: Array<String>) {
    SpringApplication.run(AlexandrieApp::class.java, *args)
}

@Configuration
class Configuration{
    @Bean
    fun kotlinJackson(): Module = KotlinModule()

}

@EnableWebSecurity
class WebSecurityConfig : WebMvcConfigurerAdapter() {

    protected fun configure(http: HttpSecurity) {
        http.cors().and()
                .authorizeRequests()
                .anyRequest().anonymous()
    }
}
