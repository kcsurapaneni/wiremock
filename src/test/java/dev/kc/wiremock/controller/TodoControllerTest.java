package dev.kc.wiremock.controller;

import com.maciejwalkowiak.wiremock.spring.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableWireMock({
        @ConfigureWireMock(name = "todo-controller", property = "url.jsonplaceholder", stubLocation = "wiremock/todo-service")
})
class TodoControllerTest {

    @Autowired
    TodoController todoController;

    @Autowired
    MockMvc mockMvc;


    @Test
    void getUsers() throws Exception { // WireMock will use response from given `stubLocation`
        mockMvc
                .perform(MockMvcRequestBuilders.get("/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("first"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].completed").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("second"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].completed").value(false));
    }
}