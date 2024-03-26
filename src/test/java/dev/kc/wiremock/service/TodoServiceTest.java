package dev.kc.wiremock.service;

import com.github.tomakehurst.wiremock.*;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.maciejwalkowiak.wiremock.spring.*;
import dev.kc.wiremock.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@EnableWireMock({
        @ConfigureWireMock(name = "todo-service", property = "url.jsonplaceholder")
})
class TodoServiceTest {

    @InjectWireMock("todo-service")
    private WireMockServer wireMockServer;

    @Autowired
    TodoService todoService;

    @Test
    void fetchUsersData() {
        // Given
        wireMockServer
                .stubFor(WireMock.get("/todos")
                        .willReturn(
                                WireMock.aResponse()
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("""
                                                [
                                                  {
                                                    "userId": 1,
                                                    "id": 1,
                                                    "title": "first",
                                                    "completed": true
                                                  },
                                                  {
                                                    "userId": 1,
                                                    "id": 2,
                                                    "title": "second",
                                                    "completed": false
                                                  }
                                                ]
                                                """)
                        )
                );

        // When
        List<User> users = todoService.fetchUsersData();

        // Then
        assertThat(users)
                .isNotNull()
                .hasSize(2);
        // first
        User firstRecord = users.getFirst();
        assertThat(firstRecord.id()).isEqualTo(1);
        assertThat(firstRecord.userId()).isEqualTo(1);
        assertThat(firstRecord.title()).isEqualTo("first");
        assertThat(firstRecord.completed()).isTrue();

        // second
        User secondRecord = users.getLast();
        assertThat(secondRecord.id()).isEqualTo(2);
        assertThat(secondRecord.userId()).isEqualTo(1);
        assertThat(secondRecord.title()).isEqualTo("second");
        assertThat(secondRecord.completed()).isFalse();
    }

    @Test
    void fetchUsersDataUsingDefaultStubLocation() {
        // When
        List<User> users = todoService.fetchUsersData();

        // Then
        assertThat(users)
                .isNotNull()
                .hasSize(2);
        // first
        User firstRecord = users.getFirst();
        assertThat(firstRecord.id()).isEqualTo(1);
        assertThat(firstRecord.userId()).isEqualTo(1);
        assertThat(firstRecord.title()).isEqualTo("first");
        assertThat(firstRecord.completed()).isTrue();

        // second
        User secondRecord = users.getLast();
        assertThat(secondRecord.id()).isEqualTo(2);
        assertThat(secondRecord.userId()).isEqualTo(1);
        assertThat(secondRecord.title()).isEqualTo("second");
        assertThat(secondRecord.completed()).isFalse();
    }

}