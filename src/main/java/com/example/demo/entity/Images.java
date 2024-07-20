package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String rute;
    String businessUuid;
}
