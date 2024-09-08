# Expass 2
_**Thea Jenny E. Kolnes**_

I've mainly divided the application in two packages: components and controllers.
* Components stores the classes for: Poll, User, Vote and VoteOption
* Controllers stores the classes: PollController, UserController and VoteController.

The component classes controls how the components are built and stores the get and set methods. I've used `@JsonIgnore` and `@JsonProperty` to keep control of the de-/serialization of the parameters from the json requests to the API. 

The controllers stores the handlers to the classes that receives the different requests (`GET`, `POST`, `PUT` and `DELETE`) and acts upon them.

The `PollManager` controls the logic and stores all the data. The controllers communicate with the PollManager to execute the request that is received. It stores 4 hashmaps:
* `Map<String, User> users = HashMap.newHashMap(2);` 
* `Map<UUID, Poll> polls = HashMap.newHashMap(2);` 
* `Map<UUID, User> userPolls = HashMap.newHashMap(2);` 
* `Map<String, Vote> pollVotes = HashMap.newHashMap(2);`

The reason for these hashmaps is mainly to keep control of the unique users and polls, in addition to keep track of the connection of the user (creator) of the poll and the users who have casted votes.

For the testing I've tried two different test approaches. 
* The first one is a [http client](src/main/java/no/hvl/rest/RequestsSeq.http), where all http request can be executed when the application is running. Each test checks the server response with a certain assertion. While it does check the status responses and the length of the responses for all the cases we were given by the submission, it lacks the details of the feedback in the way I desired. (If you're testing this, remember to run with `dev` environment, then click `run all requests in file`.)
* The second is the [Springboot Test](src/test/java/no/hvl/rest/PollApplication.java). The test are standalone, and the application doesn't need to run to run the test. This test can get more details on the test feedback. While it works in a great degree, I struggled to make it work the way I wanted. Some tests that worked on the http client was somehow difficult to set up in the SpringBoot test. 

### Technical problems that you encountered during installation and how you resolved
I didn't have any specific problems during installation.

### Any pending issues with this assignment that you did not manage to solve
The logic and how many edge cases we were supposed to "care" about.
* I ended up spending too much time on coding the test that I didn't have time for certain edge cases like: vote only being valid between published date and validUntil date.
* I didn't have time for implementing authentication (headers for checking if the user was logged in), and when the poll is private the poll creator needs to send an invitation. 

Setting up certain tests with Springboot.
* It was a bit challenging to decide how to set up the tests, whether to have them one test for the whole poll flow, or multiple smaller test focused on specific tasks.
* In the end, I ended up making one big test which is the main test that tests the cases we were given by the submission. However, I struggled to set up test for user voting on a poll in the SpringBoot test (somehow it works on the http client tests).