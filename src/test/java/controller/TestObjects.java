package controller;

import com.example.dater.model.Log;
import com.example.dater.model.Settings;

public class TestObjects {

    public static Settings createSetting() {
        return new Settings().setIsEmailActive(false).setEmailAddress("email@gmail.com");

    }
    public static Log createLog() {
        return new Log().setSentToAddress("person@gmail.com").setInitiatedBy("admin").setDate("2022-02-19T13:26:13.836Z").setMailContent("[mail]").setSchedulerValue(10);

    }

}


