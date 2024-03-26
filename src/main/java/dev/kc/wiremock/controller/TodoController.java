package dev.kc.wiremock.controller;

import dev.kc.wiremock.dto.*;
import dev.kc.wiremock.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Krishna Chaitanya
 */
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @GetMapping
    List<User> getUsers() {
        return todoService.fetchUsersData();
    }

}
