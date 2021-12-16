package com.example.demo

import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@WebMvcTest
class UserControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var userService: UserService

    @Test
    fun returnsAllUsers() {
        val users: MutableList<User> = ArrayList()
        users.add(User(1L, "Joel Karlsson", true))
        users.add(User(2L, "Jesus", false))
        every { userService.findAll() } returns users
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(jsonPath("$", hasSize<Any>(2)))
            .andDo(print())
    }
}