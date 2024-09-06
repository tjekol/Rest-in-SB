package no.hvl.rest.components;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Poll  {
    @JsonIgnore private UUID id;
    private String username; // username to user who created poll
    private String question;
    @JsonIgnore private Instant publishedAt;
    private Instant validUntil;
    private boolean isPublic;
    private Set<VoteOption> voteOptions;

    public Poll(
            @JsonProperty("username") String username,
            @JsonProperty("question") String question,
            @JsonProperty("validUntil") Instant validUntil,
            @JsonProperty("isPublic") boolean isPublic,
            @JsonProperty("voteOptions") Set<VoteOption> voteOptions
    ) {
        this.id = UUID.randomUUID(); // random unique ID
        this.username = username;
        this.question = question;
        this.publishedAt = Instant.now();
        this.validUntil = validUntil;
        this.isPublic = isPublic;
        this.voteOptions = voteOptions;
    }

    public Poll() {};

    public UUID getPollID() {
        return id;
    }

    // should not be able to change user creator
    public String getPollCreator() {
        return username;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Set<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public void addVoteOption(VoteOption vo) {
        voteOptions.add(vo);
    }
}

