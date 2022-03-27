package controller;

import com.example.dater.model.Event;
import com.example.dater.model.Log;
import com.example.dater.model.Settings;

import java.time.Instant;

import static com.example.dater.service.SendMailServiceImpl.MESSAGE_TYPE_MAIL;

public class TestObjects {

    public static Settings createSetting() {
        return new Settings()
                .setIsEmailActive(false)
                .setEmailAddress("email@gmail.com")
                .setIsSmsActive(false)
                .setSmsTo("+372 1234567");
    }

    public static Log createLog() {
        return new Log()
                .setSentToAddress("person@gmail.com")
                .setInitiatedBy("admin")
                .setDateCreated(Instant.parse("2022-02-19T13:26:13.836Z"))
                .setMessageContent("[mail]")
                .setSchedulerValue(10)
                .setMessageType(MESSAGE_TYPE_MAIL);
    }

    public static Event createEventWithoutCreatedDate() {
        return new Event().setName("Hello world!")
                .setDate(Instant.parse("2022-02-19T13:26:13.836Z"))
                .setAccountForYear(false)
                .setReminder(true)
                .setReminderDays(1);
    };

    public static Event createEventWithCreatedDate() {
        return new Event().setName("Hello world!")
                .setDate(Instant.parse("2022-02-19T13:26:13.836Z"))
                .setAccountForYear(false)
                .setReminder(false)
                .setReminderDays(1)
                .setDateCreated(Instant.now());
    }
}
