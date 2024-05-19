package com.example.demo.repository;

import com.example.demo.entity.AuditoryRevision;
import com.example.demo.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoryRevisionRepository extends JpaRepository<AuditoryRevision, String> {

}
