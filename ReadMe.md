# Dater Back-end

This is the Dater project Back-end. It is built using Spring Boot. It implements basic CRUD functionality on all models, checks all the events in the events at the given interval and if needed sends out a daily report incoming events to the user.
Every notification (mail or sms) is logged in the logging database. 

[Front-end github repo](https://github.com/Seeru-crypto/Dater_FE)


---
## Setup

1.  On first time setup, user needs to configure email address, which is used to send out emails.
    The config email security settings has to be turned on to allow outside apps to access this email acc.

        In `application.properties` Replace the username with email aadress and email password with your emaol password.

        For the configurations to work in our applications, you must configure gmail account settings to allow connections from less secure apps.

        On the Gmail account, click on manage account -> security -> turn on access to less secure apps

2.  Setup mongoDb docker

   - To build the image
       -   `docker pull mongo`
   - To run the image
       -   `docker run -d --name mongo -p 270717:27017 mongo`
   - In application.properties file set the mongoDB.uri filed as the connection string (`mongodb://localhost:270717`)

3. Start Gradle server

---

### ToDo:

- [x] Serve API endpoints
    -   [x] GET events
    -   [x] POST Events
    -   [x] DELETE Event
    -   [x] UPDATE Event
    -   [x] Add GetBy endpoints (eventDescription, date, ect).
    -   [x] Add data validation for POST requests.
- [x] During start-up server creates a custom schema to DB
- [x] Server can implement CRUD functionality to DB
- [x] Integrate React Application with Spring boot back-end
- [x] Server Sends the e-mail at a specified date.
    - [x] Server Check the dates in DB once every 24h
        -   [x] Create recurring tasks
        -   [x] Create Event date validation logic
        -   [x] add remind annually functionality
    - [ ] function that sends e-mail to designated aadress
        - [x] Emailer implementation
        - [x] email body is defined in a given template
        - [x] Create a daily report, using said templates (name of event, date and desc)
        - [x] Sent emails are logged
        - [ ] [Testing emails](https://mailtrap.io/blog/spring-send-email),
    - [ ] server sends sms notification if the sms value is selected. 
      - [ ] sms integration tests are done. 
      - [ ] sms model structure is implemented
      - [ ] sms funcationlity is implemented, using twilio
- [x] Create admin object model.
- [x] Add Send email REST API endpoint.
- [x] Add 66% test coverage
- [ ] Create server Dockerfile
- [x] Add Swagger Module
- [x] enable spring actuator with health & scheduler endpoints
- [x] make CORS settings configurable
- [x] Publish
    -   [x] Setup MongoDB server
    -   [x] Publish FE & BE in heroku.
- [ ] Security
  - [x] Add data validation to all requests where applicable
  - [ ] Add request throttling, to prevent DDOS and malicous spamming. 
  - [ ] Add proper user registration and authentication, using auth0 (Realised in module 3)
- 
### PORTS

- Front uses port 5000
- Back-end uses 5005
- MongoDB uses port 270717
