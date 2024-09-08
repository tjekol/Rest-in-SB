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

    public boolean deletePoll(UUID pollID) {
        if (pollExists(getPollByID(pollID))) {
            polls.remove(pollID);
            userPolls.remove(pollID);
            for (Vote vote : pollVotes.values()) {
                if (vote.getPollID().equals(pollID)) {
                    pollVotes.remove(vote.getVoter());
                }
            }
            return true;
        } else {
            return false;
        }
    }


    public boolean castVote(Vote vote) {
        Poll poll = getPollByID(vote.getPollID());
        if (pollExists(poll)) {
            if (poll.isPublic()) {
                String voter = vote.getVoter();
                if (voter == null || voter.equals("")) {
                    voter = UUID.randomUUID().toString();
                    vote.setVoter(voter);
                }
                pollVotes.put(voter, vote);
            } else {
                User voter = getUserByUsername(vote.getVoter());
                if (userExists(voter)) {
                    pollVotes.put(voter.getUsername(), vote);
                } else {
                    return false;
                }
            }
            return true; // vote was cast
        } else {
            return false; // poll/voter doesn't exist, no vote cast
        }
    }
}
