package com.example.demo.entity;

import com.example.demo.config.MyRevisionListener;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Data
@Entity
@RevisionEntity(MyRevisionListener.class)
public class AuditoryRevision extends DefaultRevisionEntity {
  @Id
  private int id;
  private long timestamp;
  private String modifierUser;
  private String ip;
  private String hostname;
}
