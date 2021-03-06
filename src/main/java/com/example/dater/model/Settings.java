package com.example.dater.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Settings {
    public static final int MAX_EMAIL_LEN = 35;
    public static final String EMAIL_REGEX = "^[\\w!#$%&’*+\\/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+\\/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String PHONE_NR_REGEX = "\\+[0-9]{1,3} [0-9]{7,10}";
    @Id
    private String id;
    @NotNull
    private Boolean isEmailActive;
    @NotBlank(message = "Email is mandatory")
    @Size(max = MAX_EMAIL_LEN, message = "Email is too long")
    @Email(message = "invalid email", regexp = EMAIL_REGEX)
    private String emailAddress;
    private Instant dateUpdated;
    @Pattern(regexp = PHONE_NR_REGEX, message = "invalid phone number" )
    private String smsTo;
    @NotNull
    private Boolean isSmsActive;
}
