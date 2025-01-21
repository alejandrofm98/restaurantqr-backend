package com.example.demo.features.business;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.envers.Audited;

@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="business")
public class Business implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    @Id
    @Column(name = "business_uuid" , unique = true, nullable = false)
    String businessUuid;
    String name;
    String address;
    String country;
    String documentNumber;
    String documentType;
    boolean isActive;
    String lenguajeIso2;
    String lenguajeIso3;
    String postalCode;
    String province;
    String state;
    String town;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;



    @PrePersist
    protected void onCreate() {
        this.businessUuid = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }
    public boolean setIsActive(boolean isActive) {
        boolean previousValue = this.isActive;
        this.isActive = isActive;
        return previousValue;
    }

}
