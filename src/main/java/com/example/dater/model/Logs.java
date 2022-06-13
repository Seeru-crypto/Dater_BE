package com.example.dater.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class Logs {
    @Id
    private String id;
    @NotNull
    private Instant dateCreated;
    @NotNull
    private String sentToAddress;
    @NotNull
    private String initiatedBy;
    @NotNull
    private String messageContent;
    @NotNull
    private Integer schedulerValue;
    private String errorDesc;
    @NotNull
    private String messageType;

    @Override
    public String toString() {
        return String.format(
                "Event[id='%s', sentToAddress='%s', dateCreated='%s', initiatedBy='%s', messageContent='%s', errorDesc='%s']",
                id, sentToAddress, dateCreated, initiatedBy, messageContent, errorDesc);
    }

}
