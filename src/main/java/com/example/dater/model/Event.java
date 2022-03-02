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
    @Id
    private String id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 26, message = "Name is mandatory")
    private String name;
    @Size(min = 20, max = 26, message = "date value is incorrect")
    @NotNull
    private String date;
    private Instant dateCreated;
    private Instant dateUpdated;
    @NotNull
    private Boolean reminder;
    @Max(31)
    @NotNull
    private Integer reminderDays;
    @Size(max = 121, message = "desc len is too high")
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
