# Expass 5
_**Thea Jenny E. Kolnes**_

CRUD operations in MongoDB.

## Setup
Validate package
![setup](images/setup.png)

Connect to database
* `brew services start mongodb-community@7.0`
* `mongosh`
![connect](images/start.png)

### Technical problems that you encountered during installation and use of MongoDB and how you resolved
Had none.

## Experiment 1, CRUD operations

* Insert
  * ![insert](images/insert.png)
* Update
  * ![update](images/update.png)
* Query
  * ![query](images/query.png)
* Delete
  * ![delete](images/delete.png)
* Bulk write
  * ![bulk-write](images/bulk-write.png)

## Experiment 2, Aggregation
* Aggregation
  * ![aggregation](images/aggregation.png)
* Map-Reduce
  * ![map-reduce](images/map-reduce.png)

### Reason about why your implemented Map-reduce operation in Experiment 2 is useful and interpret the collection obtained.
The implementation of map-reduce is useful to avoid unnecessary duplicates or redundant data. The implementations makes every row unique and merges those who give information to the same thing. As a result, the data that is displayed is easier to understand.

The collection I received from the map-reduce in the example is the different customers ids and their total price. The collection is nice when I only care about how much each customer used in total without having to do math to find a customer's total.

### Any pending issues with this assignment which you did not manage to solve
None.