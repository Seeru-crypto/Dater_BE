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
-   [ ] Server Sends the e-mail at a specified date.
    -   [ ] Server Check the dates in DB once every 24h
        -   [x] [Create recurring tasks](https://spring.io/guides/gs/scheduling-tasks/)
        -   [x] [Get all event info from mongoDb](https://www.codementor.io/@prasadsaya/access-mongodb-database-from-a-spring-boot-application-17nwi5shuc)
        -   [x] Create date check logic
    -   [ ] function that sends e-mail to designated aadress
        -   [x] [Testing emails](https://mailtrap.io/blog/spring-send-email),
        -   [ ] [Emailer implementation](https://www.baeldung.com/spring-email)
        -   [ ] Function that that sends the email, using a given template, which uses given variables
                (name of event, date)
        -   [ ] [Sent emails are logged](https://www.baeldung.com/spring-boot-logging)
-   [ ] Create admin object model.
-   [ ] Setup Heroku CI/CD pipeline
-   [ ] Add 80% test coverage
-   [ ] Create server Dockerfile
-   [ ] [Add Swagger Module](https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api)

### MongoDB Database

-   uses port 270717
-   [x] Create DB Dockerfile, with default settings

## dev links

https://www.section.io/engineering-education/spring-boot-smtp/

The config email security settings has to be turned on to allow outside apps to access this email acc.

        Replace the email-address and email password with your actual email address and password.

        For the configurations above to work in our applications, we must set up Gmail to allow connections from less secure apps.

        On your Gmail account, click on manage account.
        On the manage account screen click on the security menu item and select turn on access to less secure apps as shown below.
