package no.hvl.rest.controllers;

import no.hvl.rest.PollManager;
import no.hvl.rest.components.Poll;
import no.hvl.rest.components.User;
import no.hvl.rest.components.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.lang.annotation.Repeatable;
import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
//@RequestMapping("/polls")
public class PollController {

    private final PollManager manager;

    public PollController(@Autowired PollManager manager){
        this.manager = manager;
    };

    @GetMapping("/polls")
    public ResponseEntity<Set<Poll>> getPolls() {
        return ResponseEntity.ok().body(manager.getPolls());
    }

    @GetMapping("/polls/{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable UUID id) {
        Poll poll = manager.getPollByID(id);
        if (manager.pollExists(poll)) {
            return ResponseEntity.ok().body(poll);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/polls")
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        if (manager.createPoll(poll, poll.getPollCreator())) {
            return ResponseEntity.ok().body(poll);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*@PostMapping("/polls/{id}")
    public ResponseEntity<Vote> castVote(@PathVariable String id, @RequestBody Vote vote) {
        if (manager.castVote(vote)) {
            return ResponseEntity.created(URI.create("/" + id)).body(vote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/polls/{id}")
    public ResponseEntity<Vote> changeVote(@PathVariable String id, @RequestBody Vote newVote) {
        if (manager.castVote(newVote)) {
            return ResponseEntity.ok().body(newVote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @DeleteMapping("/polls/{id}")
    public ResponseEntity<HttpStatus> deletePoll(@PathVariable UUID id) {
        if (manager.deletePoll(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
