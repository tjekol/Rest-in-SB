package no.hvl.rest.components;

import java.io.Serializable;
import java.time.Instant;

public class Vote implements Serializable {
    private User user; // user who cast a vote
    private Instant publishedAt;

    public Vote(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Vote() {};

    //** user who voted **//
    public User getUserVoter() {
        return user;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }
}
