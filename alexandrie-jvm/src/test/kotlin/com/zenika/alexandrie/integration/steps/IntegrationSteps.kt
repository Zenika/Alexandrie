package com.zenika.alexandrie.integration.steps

import com.zenika.alexandrie.AlexandrieApp
import cucumber.api.java.Before
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.assertj.core.api.Assertions
import org.hamcrest.core.IsNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@ContextConfiguration(classes = [AlexandrieApp::class])
@WebAppConfiguration
class IntegrationSteps {


    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mvc: MockMvc

    @Before
    fun test() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    private lateinit var searchBook: String
    private lateinit var result: ResultActions

    @Given("""^that "([^"]*)" borrowed the book "([^"]*)"$""")
    fun `borrowed the book`(userName: String, bookName: String) {
        searchBook = bookName
        mvc.perform(MockMvcRequestBuilders.put("/$bookName/borrower")
                .content("""{
                    "name":"$userName"
                    }""".trimMargin())
                .contentType("application/json")
                .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @And("""^"([^"]*)" returned the book "([^"]*)"$""")
    fun `returned the book`(userName: String, bookName: String) {
        searchBook = bookName
        mvc.perform(MockMvcRequestBuilders.delete("/$bookName/borrower")
                .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @When("""^we ask the system who has the book$""")
    fun `we ask the system who has the book`() {
        result = mvc.perform(MockMvcRequestBuilders.get("/$searchBook/borrower")
                .with(SecurityMockMvcRequestPostProcessors.anonymous()))
    }

    @Then("""^it should answer "([^"]*)"$""")
    fun `it should answer`(answer: String) {
        val result = result.andExpect(MockMvcResultMatchers.status().isOk).andReturn().response.contentAsString
        Assertions.assertThat(result).contains(answer)
    }

    @Then("""^it should answer nobody$""")
    fun `it should answer nobody`() {
        result.andExpect(MockMvcResultMatchers.status().isOk).andExpect(
                MockMvcResultMatchers.jsonPath("borrower")
                        .value(IsNull.nullValue())
        )
    }

}
