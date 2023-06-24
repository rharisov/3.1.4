package org.example;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Log {
    private final RestTemplate restTemplate = new RestTemplate();

    private final User user = new User(3L, "James", "Brown", (byte) 0);
    private final String URL = "http://94.198.50.185:7081/api/users";
    private final HttpHeaders headers = new HttpHeaders();

    public void findAllUsers() {


        ResponseEntity<List<User>> response = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });

        String sessionId = response.getHeaders().getFirst("Set-Cookie");

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add("Cookie", sessionId);
    }

    public String createUser() {

        HttpEntity<User> post = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, post, String.class);

        return response.getBody();
    }

    public String editUser() {
        HttpEntity<User> post = new HttpEntity<>(user, headers);

        user.setName("Thomas");
        user.setLastName("Shelby");
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, post, String.class);
        return response.getBody();
    }

    public String deleteUser() {

        HttpEntity<User> post = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, post, String.class);

        return response.getBody();

    }

    public String allMethods() {
        findAllUsers();
        return createUser()+editUser()+deleteUser();

    }
}
