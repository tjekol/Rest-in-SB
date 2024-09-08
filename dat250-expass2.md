# Expass 2
_**Thea Jenny E. Kolnes**_

I've tried two different test approaches. 
* The first one is a [http client](src/main/java/no/hvl/rest/RequestsSeq.http), where all http request can be executed when the application is running. Each test checks the server response with a certain assertion. While it does check the status responses, it can not check the feedback in the way I wanted.
* The second is the [Springboot Test](src/test/java/no/hvl/rest/PollApplication.java). The test are standalone, and the application doesn't need to run to run the test. This test can get more details on the test feedback, but it 

### Technical problems that you encountered during installation and how you resolved
I didn't have any specific problems during installation.

### Any pending issues with this assignment that you did not manage to solve
The logic and how many edge cases we were supposed to "care" about.

Setting up certain tests with Springboot.
* It was also a bit challenging to decide how to set up the test, whether to have them one test for the whole poll flow, or multiple smaller test on specific tasks.
* In the end, I ended up making one big test for the cases we were given by the submission. However, I struggled to set up test for user creating a poll.