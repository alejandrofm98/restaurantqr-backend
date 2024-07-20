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
@Table(name ="subcategory", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "businessUuid"})
})
public class SubCategory {
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