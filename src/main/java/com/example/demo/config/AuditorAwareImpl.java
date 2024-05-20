package com.example.demo.config;

import com.example.demo.entity.AuditoryRevision;
import com.example.demo.jwt.JwtService;
import io.jsonwebtoken.Jwt;
import io.micrometer.common.lang.NonNullApi;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.envers.RevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;


@Log4j2
@NonNullApi
@RequiredArgsConstructor
@Component
public class AuditorAwareImpl implements AuditorAware<AuditoryRevision> {

  private final JwtService jwtService;

  private static final String[] IP_HEADER_CANDIDATES = {
      "X-Forwarded-For",
      "Proxy-Client-IP",
      "WL-Proxy-Client-IP",
      "HTTP_X_FORWARDED_FOR",
      "HTTP_X_FORWARDED",
      "HTTP_X_CLUSTER_CLIENT_IP",
      "HTTP_CLIENT_IP",
      "HTTP_FORWARDED_FOR",
      "HTTP_FORWARDED",
      "HTTP_VIA",
      "REMOTE_ADDR"};

  @Override
  public Optional<AuditoryRevision> getCurrentAuditor() {
    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request;
    try {
      assert sra != null;
      request = sra.getRequest();
    } catch (NullPointerException e) {
      log.error(e);
      return Optional.empty();
    }
    AuditoryRevision auditoryRevision = new AuditoryRevision();
    auditoryRevision.setIp(getClientIpAddress(request));
    auditoryRevision.setHostname(request.getRemoteHost());
    auditoryRevision.setModifierUser(getUsername(request));

    return Optional.of(auditoryRevision);
  }


  private String getClientIpAddress(HttpServletRequest request) {
    for (String header : IP_HEADER_CANDIDATES) {
      String ip = request.getHeader(header);
      if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
        return ip;
      }
    }
    return request.getRemoteAddr();
  }

  private String getUsername(HttpServletRequest request) {
    return jwtService.getUsernameFromToken(jwtService.getTokenFromRequest(request));
  }

}
