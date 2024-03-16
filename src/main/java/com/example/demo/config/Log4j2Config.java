package com.example.demo.config;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Supplier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class Log4j2Config {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logRequestInfo(String method, String endpoint,
                                      String response, String request) {
        LocalDateTime now = LocalDateTime.now();
        log.info("--------------Inicio--------------------");
        log.info("[ENDPOINT]"+endpoint+"[/ENDPOINT]"+"[METHOD]"+method+"[/METHOD]"+"[DATE]"+now.format(formatter)+
                "[/DATE]"+"[REQUEST]"+request+"[/REQUEST]"+"[RESPONSE]"+response+"[/RESPONSE]");
    }



    public static void logRequestError(String message) {
        log.info("--------------Inicio--------------------");
        log.error(message);
    }


    public static void logRequestError(String method, String endpoint,
                                       String request,
                                       String errorDescription) {
        LocalDateTime now = LocalDateTime.now();
        boolean error = true;
        log.info("--------------Inicio--------------------");
        log.error("[ENDPOINT]"+endpoint+"[/ENDPOINT]"+"[METHOD]"+method+"[/METHOD]"+"[DATE]"+now.format(formatter)+
                "[/DATE]"+"[REQUEST]"+request+"[/REQUEST]"
        +"[ERROR]"+error+"[/ERROR]"+"[ERRORDESCRIPTION]"+errorDescription+"[/ERRORDESCRIPTION]");
    }


}
