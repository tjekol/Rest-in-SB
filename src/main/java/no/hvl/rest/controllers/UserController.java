package no.hvl.rest.controllers;

import no.hvl.rest.PollManager;
import no.hvl.rest.components.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
//@RequestMapping("/users")
public class UserController {

    private final PollManager manager;

    public UserController(@Autowired PollManager manager) {
        this.manager = manager;
    };

    @GetMapping("/users")
    public ResponseEntity<Set<String>> getUsers() {
        return ResponseEntity.ok(manager.getUsers());
    }

    @PostMapping("/users/{username}")
    public ResponseEntity<User> createUser(@PathVariable String username, @RequestBody User newUser) {
        if (manager.createUser(newUser)) {
            return ResponseEntity.created(URI.create("/" + username)).body(newUser);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(newUser);
        }
    }
}
