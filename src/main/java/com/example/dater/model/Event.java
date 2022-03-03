package com.example.dater.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
public class Event {
    public static final int MAX_DESC_LEN = 121;
    public static final int MAX_NAME_LEN = 26;
    public static final int REMINDER_DAYS_MAX_VALUE = 31;

    @Id
    private String id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = MAX_NAME_LEN, message = "Name is mandatory")
    private String name;
    @Size(min = 20, max = 26, message = "date value is incorrect")
    @NotNull
    private String date;
    private Instant dateCreated;
    private Instant dateUpdated;
    @NotNull
    private Boolean reminder;
    @Max(REMINDER_DAYS_MAX_VALUE)
    @NotNull
    private Integer reminderDays;
    @Size(max = MAX_DESC_LEN, message = "desc len is too high")
    private String description;
    @NotNull
    private Boolean accountForYear;

    @Override
    public String toString() {
        return String.format(
                "Event[id='%s', name='%s', date='%s', reminder='%s', reminderDays='%s', description='%s', accountForYear='%s', dateCreated='%s', dateUpdated='%s']",
                id, name, date, reminder, reminderDays, description, accountForYear, dateCreated, dateUpdated);
    }
}
