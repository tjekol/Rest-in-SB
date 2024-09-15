# Expass 2
_**Thea Jenny E. Kolnes**_

Updated `PollManager` that controls the logic and stores all the data. Now it stores 5 hashmaps:
* `Map<String, User> users = HashMap.newHashMap(2);` 
* `Map<UUID, Poll> polls = HashMap.newHashMap(2);` 
* `Map<UUID, Vote> votes = HashMap.newHashMap(2);` 

* `Map<UUID, User> userPolls = HashMap.newHashMap(2);` 
* `Map<UUID, Set<Vote>> pollVotes = HashMap.newHashMap(2);`

This was to create the logic of fetching voteOptions in an easier way. I also created a voteOptionController to separate Poll og VoteOptions more.

### Technical problems that you encountered during the completion of the tutorial
* Setting up the frontend folder and backend folder in a way that the backend could be run from the root folder.
* Sometimes when creating a poll it would duplicate the poll and its vote options.
* Casting a vote would only cast vote on the vote option with the presentationOrder 0.

### Link to your code for experiments 1-2 above
* The frontend folder contains the Svelte [project](frontend). 
  * The [App.svelte](frontend/src/App.svelte) file, which is the "index" page.
  * The store folder contains [user.js](frontend/src/store/user.js) which is used to check if the user is logged in and stores the current user on localStorage.
* The lib folder contains the different components.
  * [CreatePoll.svelte](frontend/src/lib/CreatePoll.svelte) shows the user input fields to create a poll and created the actual poll when the user submits the form.
  * [CreateUser.svelte](frontend/src/lib/CreateUser.svelte) shows the user input fields to create a user or log in. When the user submits the form it either creates a user or logs in.
  * [Polls.svelte](frontend/src/lib/Polls.svelte) fetches the all polls and displays them.
  * [Vote.svelte](frontend/src/lib/Vote.svelte) displays the different vote options of the poll. Each vote option has a vote button that sends a POST request and casts a vote on the clicked vote option.

### Any pending issues with this assignment which you did not manage to solve

