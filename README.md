# 57blocks

This homework consist about creating an API (REST or GraphQL) That does:

* Create New Users and store them in the DB
* Authenticate the existing Users
* For authenticated user: create Movies and store them in the DB -> Movies can have private or public visibility
* For authenticated user: update/modify Movies already stored and that were created by the user that attempts to modify it

Since we are handling authentication process, the API also should send back a meaningful message any time there is an error, i.e.:

* Authentication Errors
* Creating/Updating Errors

## 1. Solution

This project solves and meet the requirements by starting a ***GraphQL service*** written in ***Java***. To achieve that, different libraries/dependencies
were needed; however the core of the API are:

 * [Spring Framework](https://spring.io/) using different Spring Boot libs
 * [DGS Framework](https://netflix.github.io/dgs/) from Netflix, that as built to be used with Spring

### 1.1 How to run the project locally

You must have at least `Java SDK 11` installed in your computer, along with `gradle 6.8.3` at least since this is the tasks and dependencies' manager
used on this project. we recommend `sdkman` which is the `home brew` for linux.

Once you meet these specifications, you are ready to start up the server locally. For this, there are two ways
to do it:
1. Using an IDE
2. Through a CLI/Linux Terminal

#### Option 1: Using an IDE

The recommended IDE is IntelliJ. Just import the project as a gradle project with Spring, and it will start to index all the files
and download the dependencies. Once ot has finished, ***only for the very first time*** you run the project, since it is a Spring project
you must go to the main class of your application; you can identify it because is the only one that has the annotation `@SpringBootApplication`
and from there search for the `main` method.

On the left-hand side of the method's signature you will find a green play button :arrow_forward:. **Click it, and that's all!! pretty easy**.
It will start running, and in the top right corner of the toolbar there is the project configuration box 
(like in [this image](https://intellij-support.jetbrains.com/hc/en-us/community/posts/360000129370/comments/360000132470)) 
and from there you can start or stop the application/service.

#### Option 2: From CLI/Linux terminal

Open a new Linux terminal window, and go to the main folder of the project. In this case would me something like: 
```shell
cd ~/<path_to_where_you_downloaded_the_repo>/hw57blocks
```
Once there, you run
```shell
gradle bootRun
```
It will start to run up the service.

## 2. GraphQL API

### 2.1 Queries & Mutations

GraphQl is different from well known REST APIs, due to two main things:
1. Has only one endpoint and 
2. It only uses one HTTP method: POST for all the requests

DGS by default has the API endpoint it to be `/graphql`. In addition, in GraphQL APIs, clients do not make traditional request with different HTTP methods
but clients do ***Queries*** and ***Mutations***.

#### Queries
Queries are the way a client that connects to a GraphQl API, retrieves, fetches or get info. In HTTP methods, would be equivalent to
GET

#### Mutations
Mutations are the way client that connects to a GraphQl API, can create, update/modify existing data. In HTTP methods, would be equivalent to
PUT, POST, PATCH

For both of these operations, you can pass input parameters that the server can process if they are configured to do so. These are examples of queries and mutations' syntax:

```graphql
mutation M2 {
  updateMovie(movie: {
    name: "Scream2", 
    releaseYear: 3029, 
    budget: 0, 
    category: SCIENCE_FICTION, 
    director: "Edwin Collazos", 
    visibility: PRIVATE
  })
}
```
And
```graphql
query Q1 {
  getPublicMovies(pagination: {size: 1, page: 1}) {
    page
    totalElements
    movies {
      name
      visibility
      director
      budget
    }
  }
}
```
For this project there are 6 different operations: 3 Queries and 3 Mutations:

| Operation | Name |
| --------- | ---- |
| getPublicMovies | Query|
| getMyPrivateMovies | Query|
| loginUser | Query|
| addMovie | Mutation|
| updateMovie | Mutation|
| registerUser | Mutation|

### 2.2 GraphQL Schemas

These schemas defines the different types that a Query/Mutation can return or accept as a parameter. Here are some examples of each one:

```graphql
extend type Mutation {
    addMovie(movie: MovieInput!): String
    updateMovie(movie: MovieInput!): String
}

extend type Query {
    getPublicMovies(pagination: Pagination): PaginatedMovie
    getMyPrivateMovies(pagination: Pagination): PaginatedMovie
}
```

Let's describe one for each operation type:
* addMovie is the name of the mutation
    - movie: is an Input param of type MovieInput, and the exclamation mark means it's mandatory
    - String: is the type of the response
    
* getPublicMovies is the name of the query
    - pagination: is an Input param of type Pagination, and this is not mandatory to execute the query
    - PaginatedMovie: is the type of the object that this operation sends back
    
You can and might check all the different schemas, under the `src/main/resource/schema/` folder

 ### 2.3 How to execute a Query/Mutation

Here we have 2 options on hwo to execute a query or mutation:
* Using a third party tool (like Postman)
* Using the embedded tool from DGS

 #### Option 1: Postman (Desktop App)

First, you must have the graphQL server up and running.Then start the Postman app. Once it's running, create a new tab to execute a new request to the
local server. As was mentioned before, by default the endpoint will be `/graphql` so redirect all the queries/mutations to that URI.
```
Method: POST 
URL: localhost:8080/graphql
```
Now in the Body tab, write the query/mutation nd the hit on send. That would be all! If you need to send any header (for example the Authorization header)
just add it on the Headers type. In this project in order to know which user is authenticated, the API needs a JWT (JSON Web Toke) to validate and execute the operation.
You must use the ``Authentication`` header with the value `"Bearer <JWT>"`.
```
Please notice and do not ommit the space between the word Bearer and the JWT
```

#### Option 2: DGS Embedded Tool
DGS has an embedded GUI tool to execute graphQL queries/mutations. To use it just open a web browser and go to `localhost:8080/graphiql` and you will be redirected to the
embedded tool. From there you can write the queries on the upper left side panel. 

The lower left side panel, is used to pass variables on the queries/mutations and for adding headers to them as well 
(there you pass the Authentication header). For both of them, the syntax is JSON.
```json
{
  "authorization": "Bearer <JWT>"
}
```
Then just hit the play button from the upper right corner. That would be all the setup needed to execute a query/mutation.
## 3. Data Base

For this project, and since the scope it's just a technical task focused on creating and leveraging a GraphQl API up, the DB selected to be used
is an embedded one powered by `h2`. Won't extend too much on this subject, but just saying that thanks to Spring, there is no necessary to
have an SQL file where the DB schema is defined. you can read more [here](https://spring.io/guides/gs/accessing-data-jpa/).

Finally, we can upload preexisted data loaded up in the service start up. 

``NOTE: since it's an embedded DB, when the service is shut down, the data will be lost ``

## 4. Unit Test

This project also has unit tests for the core of the application (again due to the scope and intention of this project). You can run them buy executing
```shell
gradle test
```
In the root project's folder.


Please take a look and hope this can work as a good approach on how a graphQL API can be developed in Java!!
