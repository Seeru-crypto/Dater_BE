## Setup

To build & run elasticsearch DB

-   ....

To build & run Gradle back-end

-   ....

## Front-end

-   [link](https://github.com/Seeru-crypto/Dater_FE) to Dater_FE Git repo.

### Technical info

-   uses React framework, react prime components and axios.
-   Ports:
    -   prod uses: 4000
    -   dev uses: 4001

## Back-End

### Technical info

-   Ports:
    -   prod uses: 5000
    -   dev uses: 5001
-   uses Spring

### ToDo:

sending Emails seems pretty straight forward. see [link](https://mailtrap.io/blog/spring-send-email/) for in-depth how to

How to schecdule jobs with spring quartz, [link](https://www.baeldung.com/spring-quartz-schedule)

How to connect to elasticsearch index, [link](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html)
[link2](https://brightmarbles.io/blog/elasticsearch-java-clients-2/)

-   [ ] Server servers API endpoints
    -   [ ] uses Swagger automated API documentation
    -   [ ] GET events
        -   [ ] with specific ID
    -   [ ] POST Events
    -   [ ] DELETE Event
    -   [ ] UPDATE Event
-   [ ] During start-up server creates a custom schema to DB
-   [ ] Server can implement CRUD functionality to DB
-   [ ] Server Check the dates in DB once every 24h
    -   [ ] Server just sends the e-mail at a specified date.
    -   [ ] Server checks the dates and takes into account the days before variable, before sending the email.
-   [ ] function that sends e-mail to designated aadress
    -   [ ] a function that sends a email
    -   [ ] Design a unfiform template for the email, which uses given variables (name of event, date)
    -   [ ] a function that that sends the email, using a given template
-   [ ] Create server Dockerfile

### Elasticsearch Database

-   uses port 5432
-   [ ] Create DB Dockerfile, with default settings
-   [ ] create neccesary indexes indexes: - Events (Name, createdTimestamp, Date, Reminder, Reminder days, ?reminder interval, description, userId (Elasticsearch ID), Event ID (Elasticsearch ID)) - Users (email aadress, username, userId (Elasticsearch ID), password, Event limit (1-100), User Group (admin, demo, user)) - logs (user, activity, timestamp)
-   [ ] Integrate automatic test data generation into the docker file
