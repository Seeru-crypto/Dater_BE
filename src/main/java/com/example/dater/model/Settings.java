package com.example.dater.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class Settings {

    @Id
    private String id;
    @NotNull
    private Boolean isEmailActive;
    @NotBlank(message = "Email is mandatory")
    @Size(max = 35, message = "Email is too long")
    private String emailAddress;

    public Settings setSettings(Settings settings) {
        this.isEmailActive = settings.isEmailActive;
        this.emailAddress = settings.emailAddress;
        return this;
    }


    @Override
    public String toString() {
        return String.format(
                "Settings[id='%s', isEmailActive='%s', emailAddress='%s']",
                id, isEmailActive, emailAddress);
    }

}
