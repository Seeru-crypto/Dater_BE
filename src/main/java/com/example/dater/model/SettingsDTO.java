package com.example.dater.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

import static com.example.dater.model.Settings.MAX_EMAIL_LEN;

@Getter
@Setter
@NoArgsConstructor
public class SettingsDTO {
    @NotNull
    private Boolean isEmailActive;
    @Size(max = MAX_EMAIL_LEN, message = "Email is too long")
    private String emailAddress;
    private Instant dateUpdated;
    private String smsTo;
    private Boolean isSmsActive;
}
