package com.example.dater.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class Log {
    @Id
    private String id;
    @NotNull
    private Instant dateCreated;
    @NotNull
    @Email
    private String sentToAddress;
    @NotNull
    private String initiatedBy;
    @NotNull
    private String mailContent;
    @NotNull
    private Integer schedulerValue;
    private String errorDesc;

    @Override
    public String toString() {
        return String.format(
                "Event[id='%s', sentToAddress='%s', dateCreated='%s', initiatedBy='%s', mailContent='%s', errorDesc='%s']",
                id, sentToAddress, dateCreated, initiatedBy, mailContent, errorDesc);
    }

}
