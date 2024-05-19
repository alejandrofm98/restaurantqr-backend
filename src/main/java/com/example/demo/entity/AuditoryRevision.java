package com.example.demo.entity;

import com.example.demo.config.MyRevisionListener;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "auditory_revision", catalog = "auditory")
@RevisionEntity(MyRevisionListener.class)
public class AuditoryRevision extends DefaultRevisionEntity {
  @Id
  private int id;
  private long timestamp;
  private String modifierUser;
  private String ip;
  private String hostname;
}
