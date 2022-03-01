//package com.example.dater.helperFunction;
//
//import org.bson.json.Converter;
//
//import java.time.OffsetDateTime;
//import java.util.Date;
//
//public class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, Date> {
//    @Override
//    public Date convert(OffsetDateTime offsetDateTime){
//        return Date.from(offsetDateTime.toInstant());
//    }
//}
