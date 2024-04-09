package com.example.demo.config;

import com.example.demo.utils.Constants;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Log4j2Config {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");


  public static void logRequestInfo(String method, String endpoint,
      String response, String request) {
    LocalDateTime now = LocalDateTime.now();
    log.info(Constants.LOGS_START_MESSAGE);
    log.info(Constants.LOGS_ENDPOINT_START + endpoint + Constants.LOGS_ENDPOINT_FINISH +
        Constants.LOGS_METHOD_START + method + Constants.LOGS_METHOD_START +
        Constants.LOGS_DATE_START + now.format(formatter) + Constants.LOGS_DATE_FINISH +
        Constants.LOGS_REQUEST_START + request + Constants.LOGS_REQUEST_FINISH +
        Constants.LOGS_RESPONSE_START + response + Constants.LOGS_RESPONSE_FINISH);
  }


  public static void logRequestError(String message) {
    log.info(Constants.LOGS_START_MESSAGE);
    log.error(message);
  }


  public static void logRequestError(String method, String endpoint,
      String request,
      String errorDescription) {
    LocalDateTime now = LocalDateTime.now();
    boolean error = true;
    log.info(Constants.LOGS_START_MESSAGE);
    log.error(Constants.LOGS_ENDPOINT_START + endpoint + Constants.LOGS_ENDPOINT_FINISH +
        Constants.LOGS_METHOD_START + method + Constants.LOGS_METHOD_FINISH +
        Constants.LOGS_DATE_START + now.format(formatter) + Constants.LOGS_DATE_FINISH +
        Constants.LOGS_REQUEST_START + request + Constants.LOGS_REQUEST_FINISH +
        Constants.LOGS_ERROR_START + error + Constants.LOGS_ERROR_FINISH +
        Constants.LOGS_ERROR_DESCRIPTION_START + errorDescription
        + Constants.LOGS_ERROR_DESCRIPTION_FINISH);
  }

  public static void logMethod(String function,
      String description) {
    LocalDateTime now = LocalDateTime.now();
    log.info(Constants.LOGS_START_MESSAGE);
    log.info(Constants.LOGS_FUNCTION_START + function + Constants.LOGS_FUNCTION_FINISH +
        Constants.LOGS_DATE_START + now.format(formatter) + Constants.LOGS_DATE_FINISH +
        Constants.LOGS_DESCRIPTION_START + description + Constants.LOGS_DESCRIPTION_FINISH +
        Constants.LOGS_ERROR_START + false + Constants.LOGS_ERROR_FINISH);

  }

  public static void logMethod(String function,
      String description, String errorDescription) {
    LocalDateTime now = LocalDateTime.now();
    log.info(Constants.LOGS_START_MESSAGE);
    log.error(Constants.LOGS_FUNCTION_START + function + Constants.LOGS_FUNCTION_FINISH +
        Constants.LOGS_DATE_START + now.format(formatter) + Constants.LOGS_DATE_FINISH +
        Constants.LOGS_DESCRIPTION_START + description + Constants.LOGS_DESCRIPTION_FINISH +
        Constants.LOGS_ERROR_START + true + Constants.LOGS_ERROR_FINISH +
        Constants.LOGS_ERROR_DESCRIPTION_START + errorDescription
        + Constants.LOGS_ERROR_DESCRIPTION_FINISH);

  }


}
