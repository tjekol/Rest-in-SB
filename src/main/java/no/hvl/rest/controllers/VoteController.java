package no.hvl.rest.controllers;

import no.hvl.rest.PollManager;
import no.hvl.rest.components.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
//@RequestMapping("/votes")
public class VoteController {

    private final PollManager manager;

    public VoteController(@Autowired PollManager manager) {
        this.manager = manager;
    };

    @GetMapping("/votes")
    public ResponseEntity<Set<Vote>> getVotes() {
        return ResponseEntity.ok().body(manager.getVotes());
    }
}
