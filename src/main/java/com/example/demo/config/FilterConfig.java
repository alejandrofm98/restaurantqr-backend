package com.example.demo.config;

import com.example.demo.dto.response.ApiResponse;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

@Component
@WebFilter("/*")
@Log4j2
public class FilterConfig implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        Instant start = Instant.now();
        String methodName = "doFilter";
        String requestBody = "";
        if (req instanceof HttpServletRequest httpServletRequest) {
            methodName = ((HttpServletRequest) req).getMethod();
            if ("POST".equalsIgnoreCase(methodName) || "PUT".equalsIgnoreCase(methodName)) {
                requestBody = extractRequestBody(httpServletRequest);
            }
        }
        try {
            assert req instanceof HttpServletRequest;
            chain.doFilter(new CachedBodyHttpServletRequestConfig((HttpServletRequest) req, requestBody), resp);
        } catch (Exception ex) {
            String errorMessage = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : ex.getMessage();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            Log4j2Config.logRequestError(methodName, ((HttpServletRequest) req).getRequestURI(), requestBody, stackTraceAsString);
            ApiResponse apiResponse = ApiResponse.builder()
                .error(true)
                .errorDescription(errorMessage)
                .build();
            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(new Gson().toJson(apiResponse));
        } finally {
            Instant finish = Instant.now();
            long time = Duration.between(start, finish).toMillis();
            log.info("ms:"+ time);
            log.info("--------------FIN-----------------------");
        }

    }


    public static String extractRequestBody(HttpServletRequest request) throws IOException, ServletException {
        StringBuilder stringBuilder = new StringBuilder();

        // Verificar si la solicitud es de tipo 'multipart/form-data' (archivos adjuntos)
        if (isMultipartContent(request)) {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getHeader("content-disposition") != null) {
                    InputStream inputStream = part.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead;
                    while ((bytesRead = reader.read(charBuffer)) != -1) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                }
            }
        } else { // Si no es 'multipart/form-data', entonces es un formulario normal
            try (BufferedReader bufferedReader = request.getReader()) {
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        }

        // Eliminar saltos de línea y espacios en blanco innecesarios
        return stringBuilder.toString().replaceAll("\\s+", "");
    }

    private static boolean isMultipartContent(HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
    }


}


