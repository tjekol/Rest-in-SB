package no.hvl.rest.components;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

public class Vote {
    private UUID pollID;
    private String username; // user who cast a vote
    private int voteOption;
    @JsonIgnore private Instant publishedAt;

    public Vote(
            @JsonProperty("pollID") UUID pollID,
            @JsonProperty("username") String username,
            @JsonProperty("voteOption") int voteOption
    ) {
        this.pollID = pollID;
        this.username = username;
        this.voteOption = voteOption;
        this.publishedAt = Instant.now();
    }

    public Vote() {};

    public UUID getPollID() {
        return pollID;
    }

    //** username of user who voted **//
    public String getVoter() {
        return username;
    }

    public void setVoter(String voter) {
        this.username = voter;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public int getVoteOption() {
        return voteOption;
    }
}
