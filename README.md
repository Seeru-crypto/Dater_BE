## Setup

## dev links

-   [Mongo, spring REST services](https://medium.com/javarevisited/building-a-rest-service-with-spring-boot-and-mongodb-part-1-2de01e4f434d)
-   [spring -> Mongo tutorial](https://spring.io/guides/gs/accessing-data-mongodb/)
-   Sending Emails - [link](https://mailtrap.io/blog/spring-send-email/) for in-depth how to
-   Schecdule jobs with spring quartz, [link](https://www.baeldung.com/spring-quartz-schedule)

## Setup

### To build & run mongoDB Dockerifle

-   navigate to mongo docker folder
-   To build the image
    -   `docker build -t mongo .`
-   To run the image

    -   `docker run -d --name=mongo -p 37017:27017 mongo`

-   To connect with created Dockerfile, a connection string must be proved. for example
    -   `mongodb://localhost:37017`

### To start the gradle server

-   gradle Bootrun

### useful info

Front end uses ports:

-   prod uses: 4000
-   dev uses: 4001

### ToDo:

-   [ ] Server servers API endpoints
    -   [ ] uses Swagger automated API documentation
    -   [ ] GET events
        -   [ ] with specific ID
    -   [ ] POST Events
    -   [ ] DELETE Event
    -   [ ] UPDATE Event
-   [ ] During start-up server creates a custom schema to DB
-   [x] Server can implement CRUD functionality to DB
-   [ ] Server Check the dates in DB once every 24h
    -   [ ] Server just sends the e-mail at a specified date.
    -   [ ] Server checks the dates and takes into account the days before variable, before sending the email.
-   [ ] function that sends e-mail to designated aadress
    -   [ ] a function that sends a email
    -   [ ] Design a unfiform template for the email, which uses given variables (name of event, date)
    -   [ ] a function that that sends the email, using a given template
-   [ ] Create server Dockerfile

### MongoDB Database

-   uses port 5432
-   [x] Create DB Dockerfile, with default settings
-   [ ] Integrate automatic test data generation into the docker file
