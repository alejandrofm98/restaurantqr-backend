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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "business_uuid", unique = true, nullable = false)
    String business_uuid;

    String name;

    @PrePersist
    protected void onCreate() {
        this.business_uuid = UUID.randomUUID().toString();
    }
}
