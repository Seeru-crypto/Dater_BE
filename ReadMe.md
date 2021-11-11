## Setup

### To build & run mongoDB Dockerifle

-   navigate to mongo docker folder
-   To build the image
    -   `docker build -t mongo .`
-   To run the image

    -   `docker run -d --name=mongo -p 270717:27017 mongo`

-   To connect with created Dockerfile, a connection string must be proved. for example
    -   `mongodb://localhost:270717`

### To start the gradle server

-   gradle Bootrun

### useful info

Front end uses ports:

-   prod uses: 4000
-   dev uses: 4001

### ToDo:

-   [x] Server servers API endpoints
    -   [x] GET events
        -   [x] with specific ID
    -   [x] POST Events
    -   [x] DELETE Event
    -   [x] UPDATE Event
-   [x] During start-up server creates a custom schema to DB
-   [x] Server can implement CRUD functionality to DB
-   [x] Integrate React Application with Spring boot back-end
-   [ ] Server Check the dates in DB once every 24h
    -   [ ] Server just sends the e-mail at a specified date.
    -   [ ] Server checks the dates and takes into account the days before variable, before sending the email.
-   [ ] function that sends e-mail to designated aadress
    -   [ ] a function that sends a email
    -   [ ] Design a unfiform template for the email, which uses given variables (name of event, date)
    -   [ ] a function that that sends the email, using a given template
-   [ ] Create server Dockerfile
-   [ ] Add Swagger Module

### MongoDB Database

-   uses port 270717
-   [x] Create DB Dockerfile, with default settings

## dev links

-   [!!! Mongo, spring REST services](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
-   Sending Emails - [link](https://mailtrap.io/blog/spring-send-email/) for in-depth how to
-   Schecdule jobs with spring quartz, [link](https://www.baeldung.com/spring-quartz-schedule)
-   [Schedule jobs using built-in scheduler](https://spring.io/guides/gs/scheduling-tasks/)
-   [How to access mongoDB from spirng](https://www.codementor.io/@prasadsaya/access-mongodb-database-from-a-spring-boot-application-17nwi5shuc)
