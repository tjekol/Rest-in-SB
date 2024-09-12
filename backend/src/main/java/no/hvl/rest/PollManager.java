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

    public Set<User> getUsers() {
        return new HashSet<>(users.values());
    }

    public Set<Poll> getPolls() {
        return new HashSet<>(polls.values());
    }

    public Set<Vote> getVotes() {
        return new HashSet<>(votes.values());
    }

    // always check if user/poll exist before getting them
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public boolean pollExists(UUID pollID) {
        return polls.containsKey(pollID);
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public Poll getPollByID(UUID id) {
        return polls.get(id);
    }

    public boolean createUser(User user) {
        if (userExists(user.getUsername())) {
            return false; // user already exists, not created
        } else {
            users.put(user.getUsername(), user);
            return true; // user is created
        }
    }

    public boolean createPoll(Poll poll, String username) {
        if (username.equals("") || username == null || poll == null) {
            return false;
        }

        User creator = getUserByUsername(username);
        UUID pollID = poll.getPollID();

        if (creator == null) {
            return false;
        }

        if (userExists(creator.getUsername())) {
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
        if (pollExists(pollID)) {
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

    public boolean deleteAllPolls() {
        polls.clear();
        votes.clear();
        userPolls.clear();
        pollVotes.clear();
        return true;
    }

    public boolean castVote(Vote vote) {
        if (vote.getPollID() == null) {
            return false;
        }

        Poll poll = getPollByID(vote.getPollID());

        if (poll == null) {
            return false;
        }

        if (poll.isPublic()) { // public poll
            String voter = vote.getVoter();
            if (voter.equals("")) {
                voter = UUID.randomUUID().toString(); // anonymous voter
                vote.setVoter(voter);
            }
            votes.put(vote.getVoteID(), vote);
            pollVotes.putIfAbsent(vote.getPollID(), new HashSet<>());
            pollVotes.get(vote.getPollID()).add(vote);
            poll.getVoteOption(vote.getVoteOption()).addVote();

            return true;
        } else { // private poll
            Set<Vote> pollVoteSet = pollVotes.get(vote.getPollID());
            if (pollVoteSet == null) {
                pollVoteSet = new HashSet<>();
                pollVotes.put(vote.getPollID(), pollVoteSet);
            }
            userHasVoted(vote, pollVoteSet, poll);

            pollVoteSet.add(vote);
            votes.put(vote.getVoteID(), vote);
            poll.getVoteOption(vote.getVoteOption()).addVote();
        }
        return true; // vote was cast or updated
    }

    // the user has already voted, remove the old vote and add the new one
    private void userHasVoted(Vote vote, Set<Vote> pollVoteSet, Poll poll) {
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
            poll.getVoteOption(existingVote.getVoteOption()).removeVote();
        }
    }

    public boolean login(String username, String password) {
        if (userExists(username)) {
            User user = getUserByUsername(username);
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
