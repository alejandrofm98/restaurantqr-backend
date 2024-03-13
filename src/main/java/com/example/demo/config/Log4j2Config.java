package com.example.demo.config;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Supplier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class Log4j2Config {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logRequestInfo(String tipoPeticion, String endpoint, String origen,
                                      String respuestaEnviada, String datosRecibidos) {
        LocalDateTime now = LocalDateTime.now();

        log.info("--------------Inicio--------------------");
        log.info("Fecha: {}", now.format(formatter));
        log.info("Tipo petición: {}", tipoPeticion);
        log.info("Endpoint: {}", endpoint);
        log.info("Origen: {}", origen);
        log.info("Respuesta enviada: {}", respuestaEnviada);
        log.info("Datos recibidos en la petición: {}", datosRecibidos);
    }


}
