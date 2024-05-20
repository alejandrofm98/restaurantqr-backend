package com.example.demo.entity;

import com.example.demo.config.MyRevisionListener;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Data
@Entity
@Table(name = "revinfo", catalog = "auditory")
@RevisionEntity(MyRevisionListener.class)
public class AuditoryRevision {

  @Id
  @RevisionNumber
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @RevisionTimestamp
  private long timestamp;
  private String modifierUser;
  private String ip;
  private String hostname;
}
