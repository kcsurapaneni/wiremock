package dev.kc.wiremock.service;

import dev.kc.wiremock.dto.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

/**
 * @author Krishna Chaitanya
 */
@Service
public class TodoService {

    @Value("${url.jsonplaceholder}") String baseUrl;

    public List<User> fetchUsersData() {
        RestClient restClient = RestClient.builder().baseUrl(baseUrl).build();
        return Arrays.stream(restClient.get().uri("/todos").retrieve().body(User[].class)).toList();
    }

}
