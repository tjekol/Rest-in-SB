<script>
    import user from "../store/user.js";
    import vote from "../store/vote.js";

    let pollID = "";
    let username = $user.username;
    let vo = null;
    let currentError = null;

    const result = fetch("http://localhost:8080/polls").then((response) => response.json())

    const convertToDate = (timestamp) => {
        const date = new Date(timestamp);  // Convert string to Date object
        return date.toLocaleString();       // Format the Date as a localized string
    }

    const castVote = () => {
        fetch("http://localhost:8080/votes", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({pollID: pollID, username: username, voteOption: vo})
        })
        .then((res) => {
            if (res.status < 299) return res.json()
            if (res.status > 299) currentError = "Something wrong with server response";
        })
        .then((data) => {
            if (data) vote.set(data)
        })
        .catch((err) => {
            currentError = err;
            console.log("Error login in: ", err)
        })
    }
</script>

<div class="polls">
    {#await result}
        its loading…
    {:then ready}
        {#each ready as poll}
        <div class="poll">
            <h3>{poll.question}</h3>
            <p>Poll created: {convertToDate(poll.publishedAt)}</p>
            <p>Poll is valid until: {convertToDate(poll.validUntil)}</p>
            <ul class="vos">
                {#each poll.voteOptions.sort((a,b) => a.presentationOrder - b.presentationOrder) as voteOption}
                    <li>{voteOption.caption}
                        <div class="vote-details">
                            <button on:click={() => {
                                pollID = poll.pollID;
                                vo = voteOption.presentationOrder;
                                castVote();
                            }}>Vote</button>
                            <p>{voteOption.votes} Votes</p>
                        </div>
                    </li>
                {/each}
            </ul>
        </div>
        {/each}
    {:catch error}
        {error}
    {/await}
</div>

<style>
    .polls {
        display: grid;
        grid-column: min(2);
    }
    .poll {
        background-color: #e8eeeb;
        border-radius: 0.2rem;
        padding: 1rem 5rem 1rem 5rem;
        width: auto;
    }
    .vos {
        list-style: none;
        padding-inline-start: 0;
    }
    .vos li {
        border: 0.18rem solid dimgrey;
        border-radius: 0.4rem;
        margin: 0.5rem 0 0.5rem 0;
        padding: 0.5rem 1rem 0.5rem 1rem;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .vote-details {
        display: flex;
        gap: 0.5rem;
        align-items: center;
    }
    /*
    .vote-details button {
        color: black;
        background-color: white;
        border: 0.1rem solid black;
        width: 3rem;
        height: 2rem;
        font-size: small;
    }*/
</style>