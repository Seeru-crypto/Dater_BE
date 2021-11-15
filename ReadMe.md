# Dater Back-end

This is the Dater project Back-end server. It is built using Spring Boot, thymeleaf and implements basic CRUD functionality, checks all the events in the database daily and if needed sends out a daily report incomming events to the user.

---

## Setup

1.  On first time setup, user needs to configure email aadres, which is used to send out emails.
    The config email security settings has to be turned on to allow outside apps to access this email acc.

        In `application.properties` Replace the email-address and email password with your actual email address and password.

        For the configurations above to work in our applications, we must set up Gmail to allow connections from less secure apps.

        On your Gmail account, click on manage account -> security -> turn on access to less secure apps

2.  Setup mongoDb docker

-   navigate to mongo docker folder

-   To build the image
    -   `docker build -t mongo .`
-   To run the image

    -   `docker run -d --name=mongo -p 270717:27017 mongo`

-   To test the connection, a connection string can be used `mongodb://localhost:270717`

3. Start Gradle server

---

## useful info

Front end uses ports:

-   prod uses: 4000
-   dev uses: 4001

### ToDo:

-   [ ] Server servers API endpoints
    -   [x] GET events
        -   [x] with specific ID
    -   [x] POST Events
    -   [x] DELETE Event
    -   [x] UPDATE Event
    -   [x] Add GetBy endpoints (Description, date, ect). 
    -   [ ] Add data validation for POST requests.
-   [x] During start-up server creates a custom schema to DB
-   [x] Server can implement CRUD functionality to DB
-   [x] Integrate React Application with Spring boot back-end
-   [ ] Server Sends the e-mail at a specified date.
    -   [x] Server Check the dates in DB once every 24h
        -   [x] [Create recurring tasks](https://spring.io/guides/gs/scheduling-tasks/)
        -   [x] [Get all event info from mongoDb](https://www.codementor.io/@prasadsaya/access-mongodb-database-from-a-spring-boot-application-17nwi5shuc)
        -   [x] Create date check logic
        -   [x] By default the checker ingores year variables.
    -   [ ] function that sends e-mail to designated aadress
        -   [x] [Testing emails](https://mailtrap.io/blog/spring-send-email),
        -   [x] [Emailer implementation](https://www.section.io/engineering-education/spring-boot-smtp/)
        -   [x] [Function that that sends the email, using a given template, which uses given variables](https://springhow.com/spring-boot-email-thymeleaf)
        -   [ ] Create a daily report, using said templates
                (name of event, date)
        -   [ ] [Sent emails are logged](https://www.baeldung.com/spring-boot-logging)
-   [ ] Create admin object model.
-   [ ] Add Send email REST API endpoint.
-   [ ] Add 66% test coverage
-   [ ] Create server Dockerfile
-   [ ] [Add Swagger Module](https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api)
-   [ ] Publish
    - [ ] Setup authentication module
    - [ ] Setup MongoDB server
    - [ ] Publish FE & BE in heroku. 

### MongoDB Database

-   uses port 270717
-   [x] Create DB Dockerfile, with default settings
