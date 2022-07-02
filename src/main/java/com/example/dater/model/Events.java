package com.example.dater.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Events {
    public static final int MAX_DESC_LEN = 121;
    public static final int MAX_NAME_LEN = 26;
    public static final int REMINDER_DAYS_MAX_VALUE = 90;
    public static final Instant MAX_DATE = Instant.parse("2040-12-30T22:00:00.000Z");
    public static final Instant MIN_DATE = Instant.parse("1959-12-31T21:00:00.000Z");

    @Id
    private String id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = MAX_NAME_LEN, message = "Name is mandatory")
    private String name;
    @NotNull
    private Instant date;
    @DateTimeFormat()
    @PastOrPresent(message = "Date created has to be in the past")
    private Instant dateCreated;
    @PastOrPresent
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
    private Instant dateNextReminder;
    private String mailDisplayDate;
}
