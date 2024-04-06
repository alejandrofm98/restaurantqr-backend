package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="business")
public class Business {

    @Id
    @Column(name = "business_uuid", unique = true, nullable = false)
    String businessUuid;

    String name;

    @PrePersist
    protected void onCreate() {
        this.businessUuid = UUID.randomUUID().toString();
    }
}
