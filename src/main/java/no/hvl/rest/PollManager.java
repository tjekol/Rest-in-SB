package no.hvl.rest;

import no.hvl.rest.components.Poll;
import no.hvl.rest.components.User;
import no.hvl.rest.components.Vote;
import no.hvl.rest.components.VoteOption;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
public class PollManager {
    private final Map<String, User> users = HashMap.newHashMap(2);
    private final Map<UUID, Poll> polls = HashMap.newHashMap(2);
    private final Map<UUID, User> userPolls = HashMap.newHashMap(2);
    private final Map<String, Vote> pollVotes = HashMap.newHashMap(2);

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

    public Set<Poll> getPolls() {
        return new HashSet<>(polls.values());
    }

    public Set<Vote> getVotes() {
        return new HashSet<>(pollVotes.values());
    }

    private User getUserByUsername(String username) {
        return users.get(username);
    }

    public Poll getPollByID(UUID id) {
        return polls.get(id);
    }

    private boolean userExists(User user) {
        return users.containsKey(user.getUsername());
    }

    public boolean pollExists(Poll poll) {
        return polls.containsKey(poll.getPollID());
    }

    public boolean createUser(User user) {
        if (userExists(user)) {
            return false; // user already exists, not created
        } else {
            users.put(user.getUsername(), user);
            return true; // user is created
        }
    }

    public boolean createPoll(Poll poll, String username) {
        User creator = getUserByUsername(username);
        UUID pollID = poll.getPollID();

        if (userExists(creator)) {
            // all polls are unique, therefore no conflicts
            polls.put(pollID, poll);
            userPolls.put(pollID, creator);
            return true;
        } else {
            return false;
        }
    }

}
