//package com.example.dater.helperFunction;
//
//import org.bson.json.Converter;
//
//import java.time.OffsetDateTime;
//import java.time.ZoneOffset;
//import java.util.Date;
//
//public class OffsetDateTimeReadConverter implements Converter<Date, OffsetDateTime> {
//    @Override
//    public OffsetDateTime convert(Date date) {
//        OffsetDateTime offsetDateTime = date.toInstant().atOffset(ZoneOffset.UTC);
//        return offsetDateTime;
//    }
//
//}
