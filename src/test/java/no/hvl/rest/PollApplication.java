package no.hvl.rest;

import no.hvl.rest.components.Poll;
import no.hvl.rest.components.User;
import no.hvl.rest.components.Vote;
import no.hvl.rest.components.VoteOption;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollApplication {

    @Autowired
    private TestRestTemplate restTemplate;

    private String user1;
    private String user2;

    @BeforeEach
    public void setUp() {
        user1 = "eple";
        user2 = "ananas";
    }

    @Test
    public void testServer() throws Exception {
        ResponseEntity<String> enitity = restTemplate.getForEntity("/", String.class);
        assertEquals(HttpStatus.OK, enitity.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = "eple")
    public void listUser(String username) throws Exception {
        ResponseEntity<Set> response = restTemplate.getForEntity("/users", Set.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(username));
    }

    @Test
    public void createUser() throws Exception {
        ResponseEntity<User> userEntity = restTemplate.postForEntity("/users/"+user1, new User(user1, "pass1", user1+"@gmail.com"), User.class);
        assertEquals(HttpStatus.CREATED, userEntity.getStatusCode());
        assertEquals(new User(user1, "pass1", user1+"@gmail.com"), userEntity.getBody());

        userEntity = restTemplate.getForEntity("/users/"+user1, User.class);
        assertEquals(HttpStatus.OK, userEntity.getStatusCode());
        assertEquals(new User(user1, "pass1", user1+"@gmail.com"), userEntity.getBody());
    }

    @Test
    public void createTwoUsers() throws Exception {
        ResponseEntity<User> userEntity1 = restTemplate.postForEntity("/users/"+user1, new User(user1, "pass1", user1+"@gmail.com"), User.class);
        ResponseEntity<User> userEntity2 = restTemplate.postForEntity("/users/"+user2, new User(user2, "pass2", user2+"@gmail.com"), User.class);
        assertEquals(HttpStatus.CREATED, userEntity1.getStatusCode());
        assertEquals(HttpStatus.CREATED, userEntity2.getStatusCode());

        // List all users, contains both users
        ResponseEntity<Set> response = restTemplate.getForEntity("/users", Set.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(user1));
        assertTrue(response.getBody().contains(user2));
    }

    @Test
    public void userPollFlow() throws Exception {
        // Create user1
        ResponseEntity<User> userEntity = restTemplate.postForEntity("/users/"+user1, new User(user1, "pass1", user1+"@gmail.com"), User.class);
        assertEquals(HttpStatus.CREATED, userEntity.getStatusCode());
        assertEquals(new User(user1, "pass1", user1+"@gmail.com"), userEntity.getBody());

        userEntity = restTemplate.getForEntity("/users/"+user1, User.class);
        assertEquals(HttpStatus.OK, userEntity.getStatusCode());
        assertEquals(new User(user1, "pass1", user1+"@gmail.com"), userEntity.getBody());

        // List all users, contains user1
        ResponseEntity<Set> response = restTemplate.getForEntity("/users", Set.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(user1));

        // Create user2
        userEntity = restTemplate.postForEntity("/users/"+user2, new User(user2, "pass2", user2+"@gmail.com"), User.class);
        assertEquals(HttpStatus.CREATED, userEntity.getStatusCode());
        assertEquals(new User(user2, "pass2", user2+"@gmail.com"), userEntity.getBody());

        // List all users, contains both users
        response = restTemplate.getForEntity("/users", Set.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(user1));
        assertTrue(response.getBody().contains(user2));

        // User1 creates a poll
        Poll poll = new Poll(
                user1,
                "Cat or Dog",
                Instant.now().plusSeconds(3600),
                false,
                new HashSet<>(Set.of(
                        new VoteOption("Cat", 0),
                        new VoteOption("Dog", 1)
                )));
        ResponseEntity<Poll> pollEntity = restTemplate.postForEntity("/polls", poll, Poll.class);
        assertEquals(HttpStatus.CREATED, pollEntity.getStatusCode());

        // Get list of polls
        response = restTemplate.getForEntity("/polls", Set.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());

        // User2 votes on poll
        //ResponseEntity<Vote> voteEntity = restTemplate.postForEntity("/votes", new Vote(poll.getPollID(), user2, 0), Vote.class);
        //assertEquals(HttpStatus.CREATED, voteEntity.getStatusCode());

        // User2 changes vote on poll
        //response = restTemplate.getForEntity("/votes", Set.class);
        //assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(1, Objects.requireNonNull(response.getBody()).size());


    }

}
