package com.example.demo.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String CONSTANT_POST = "POST";
    public static final String CONSTANT_PUT = "PUT";
    public static final String CONSTANT_DELETE = "DELETE";
    public static final String CONSTANT_GET = "GET";
    public static final String CONSTANT_URL = "http://localhost:8080";
    public static final String CONSTANT_PUBLIC_URL = "/auth";
    public static final String CONSTANT_SECURE_URL = "/api/v1";

    public static final String CONSTANT_ROL_ADMIN = "1";
    public static final String CONSTANT_ROL_USER = "2";

    public static final String CONSTANT_ROL_OWNER = "3";

    public static final Integer CONSTANT_IMAGE_MB = 4;

    public static final String EMAIL_REGISTER_TEMPLATE = "/templates/emailRegister.html";
    public static final String CLICK_2_EAT = "click2eat";


    //MENSAJES LOGS
    public static final String LOGS_START_MESSAGE = "--------------Inicio--------------------";
    public static final String LOGS_ENDPOINT_START = "[ENDPOINT]";
    public static final String LOGS_ENDPOINT_FINISH = "[/ENDPOINT]";
    public static final String LOGS_METHOD_START = "[METHOD]";
    public static final String LOGS_METHOD_FINISH = "[/METHOD]";
    public static final String LOGS_DATE_START = "[DATE]";
    public static final String LOGS_DATE_FINISH = "[/DATE]";
    public static final String LOGS_REQUEST_START = "[REQUEST]";
    public static final String LOGS_REQUEST_FINISH = "[/REQUEST]";
    public static final String LOGS_RESPONSE_START = "[RESPONSE]";
    public static final String LOGS_RESPONSE_FINISH = "[/RESPONSE]";
    public static final String LOGS_ERROR_START = "[ERROR]";
    public static final String LOGS_ERROR_FINISH = "[/ERROR]";
    public static final String LOGS_ERROR_DESCRIPTION_START = "[ERRORDESCRIPTION]";
    public static final String LOGS_ERROR_DESCRIPTION_FINISH = "[/ERRORDESCRIPTION]";
    public static final String LOGS_DESCRIPTION_START = "[DESCRIPTION]";
    public static final String LOGS_DESCRIPTION_FINISH = "[/DESCRIPTION]";
    public static final String LOGS_FUNCTION_START = "[FUNCTION]";
    public static final String LOGS_FUNCTION_FINISH = "[/FUNCTION]";

    //ROLES
    public static final String ROL_OWNER = "OWNER";
}
