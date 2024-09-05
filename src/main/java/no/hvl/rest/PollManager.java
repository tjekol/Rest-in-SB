package no.hvl.rest;

import no.hvl.rest.components.Poll;
import no.hvl.rest.components.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PollManager {
    private Map<String, User> users = new HashMap<>();
    private ArrayList<Poll> polls = new ArrayList<>();

    // Each poll is unique. User can have multiple polls.
    private final Map<Poll, User> userPolls = HashMap.newHashMap(2);

    public PollManager() {
        /*
        Poll poll = new Poll(UUID.randomUUID(), user, "Cat or Dog", Instant.now(), Instant.now());
        userPolls.put(user, poll);
        users.put(user.getUsername(), user);
        polls.add(poll);
         */
    }

    public Set<String> getUsers() {
        return users.keySet();
    }

    public Map<Poll, User> getUserPolls() {
        return userPolls;
    }

    private boolean userExists(User user) {
        return users.containsKey(user.getUsername());
    }

    public boolean addUser(User user) {
        if (userExists(user)) {
            return false; // user already exists, not added
        } else {
            users.put(user.getUsername(), user);
            return true; // user added
        }
    }

    public ArrayList<Poll> getPolls() {
        return polls;
    }

}
