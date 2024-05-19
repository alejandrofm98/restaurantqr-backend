package com.example.demo.config;

import com.example.demo.entity.AuditoryRevision;
import com.example.demo.jwt.JwtService;
import com.example.demo.repository.AuditoryRevisionRepository;
import io.micrometer.common.lang.NonNullApi;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Log4j2
@NonNullApi
@RequiredArgsConstructor
@Component
public class MyRevisionListener implements RevisionListener, ApplicationContextAware {

  private final AuditorAwareImpl auditorAware;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    //Se usa @RequiredArgsConstructor para obtener el auditorAware en vez de usar el contexto
    // llamando al Bean.
  }

  @Override
  public void newRevision(Object revisionEntity) {
    AuditoryRevision auditoryRevisionSave = (AuditoryRevision) revisionEntity;
    AuditoryRevision data = null;
    try {
      data = auditorAware.getCurrentAuditor().orElseThrow(
          () -> new RuntimeException("No se ha encontrado los datos para guardar la auditoria"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    auditoryRevisionSave.setModifierUser(data.getModifierUser());
    auditoryRevisionSave.setIp(data.getIp());
    auditoryRevisionSave.setHostname(data.getHostname());
    log.info("Guardados datos auditoria "+auditoryRevisionSave);
  }


}
