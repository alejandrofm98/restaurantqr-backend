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
@Table(name ="category", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "businessUuid"})
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    String image;
    Boolean active;
    Long offer;
    String businessUuid;

}
