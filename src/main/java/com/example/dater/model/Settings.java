package com.example.dater.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class Settings {
    public static final int MAX_EMAIL_LEN = 35;
     public static final String EMAIL_REGEX = "^[\\w!#$%&’*+\\/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+\\/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    @Id
    private String id;
    @NotNull
    private Boolean isEmailActive;
    @NotBlank(message = "Email is mandatory")
    @Size(max = MAX_EMAIL_LEN, message = "Email is too long")
    @Email(message = "Email is not valid", regexp = EMAIL_REGEX)
    private String emailAddress;
    private Instant dateUpdated;
    private String smsTo;
    private Boolean isSmsActive;

    @Override
    public String toString() {
        return String.format(
                "Settings[id='%s', isEmailActive='%s', emailAddress='%s', smsTo='%s', isSmsActive='%s']",
                id, isEmailActive, emailAddress, smsTo, isSmsActive);
    }
}
