package no.hvl.rest;

import no.hvl.rest.components.Poll;
import no.hvl.rest.components.User;
import no.hvl.rest.components.Vote;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PollManager {
    private final Map<String, User> users = HashMap.newHashMap(2);
    private final Map<UUID, Poll> polls = HashMap.newHashMap(2);
    private final Map<UUID, Vote> votes = HashMap.newHashMap(2);

    private final Map<UUID, User> userPolls = HashMap.newHashMap(2);
    private final Map<UUID, Set<Vote>> pollVotes = HashMap.newHashMap(2);

    public PollManager() {
    }

    public Set<String> getUsers() {
        return users.keySet();
    }

    public Set<Poll> getPolls() {
        return new HashSet<>(polls.values());
    }

    public Set<Vote> getVotes() {
        return new HashSet<>(votes.values());
    }

    public User getUserByUsername(String username) {
        try {
            return users.get(username);
        } catch (NullPointerException e) {
            e.fillInStackTrace();
            throw  e;
        }
    }

    public Poll getPollByID(UUID id) {
        try {
            return polls.get(id);
        } catch (NullPointerException e) {
            e.fillInStackTrace();
            throw e;
        }
    }

    public boolean userExists(User user) {
        return users.containsKey(user.getUsername());
    }

    public boolean pollExists(Poll poll) {
        if (poll == null || poll.getPollID() == null) {
            return false;
        }
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
        if (username.equals("") || username == null || poll == null || poll.getPollCreator() == null) {
            return false;
        }

        User creator = getUserByUsername(username);
        UUID pollID = poll.getPollID();

        if (userExists(creator)) {
            // all polls are unique, therefore no conflicts
            polls.put(pollID, poll);
            userPolls.put(pollID, creator);
            pollVotes.put(pollID, new HashSet<>());
            return true;
        } else {
            return false;
        }
    }

    public boolean deletePoll(UUID pollID) {
        if (pollExists(getPollByID(pollID))) {
            polls.remove(pollID);
            userPolls.remove(pollID);
            pollVotes.remove(pollID);
            for (Vote vote : votes.values()) {
                if (vote.getPollID().equals(pollID)) {
                    UUID voteID = vote.getVoteID();
                    votes.remove(voteID);
                }
            }
            return true;
        } else {
            return false;
        }
    }

 

    public boolean castVote(Vote vote) {
        if (vote.getPollID() == null) {
            return false;
        }

        Poll poll = getPollByID(vote.getPollID());

        if (poll.isPublic()) { // public poll
            String voter = vote.getVoter();
            if (voter.equals("")) {
                voter = UUID.randomUUID().toString(); // anonymous voter
                vote.setVoter(voter);
            }
            votes.put(vote.getVoteID(), vote);
            pollVotes.putIfAbsent(vote.getPollID(), new HashSet<>());
            pollVotes.get(vote.getPollID()).add(vote);
            return true;
        } else { // private poll
            User voter = getUserByUsername(vote.getVoter());

            Set<Vote> pollVoteSet = pollVotes.get(vote.getPollID());
            if (pollVoteSet == null) {
                pollVoteSet = new HashSet<>();
                pollVotes.put(vote.getPollID(), pollVoteSet);
            }
            userHasVoted(vote, pollVoteSet);

            pollVoteSet.add(vote);
            votes.put(vote.getVoteID(), vote);
        }
        return true; // vote was cast or updated
    }

    // the user has already voted, remove the old vote and add the new one
    private void userHasVoted(Vote vote, Set<Vote> pollVoteSet) {
        Vote existingVote = null;
        for (Vote pollVote : pollVoteSet) {
            if (pollVote.getVoter().equals(vote.getVoter())) {
                existingVote = pollVote;
                break;
            }
        }

        if (existingVote != null) {
            pollVoteSet.remove(existingVote); // remove old vote from the set
            votes.remove(existingVote.getVoteID()); // remove old vote from the map
        }
    }
}
