package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "Ingredient", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "productId"})
})
public class Tables {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(targetEntity = Business.class)
  @JoinColumn(name = "businessUuid", referencedColumnName = "business_uuid")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "businessUuid")
  @JsonIdentityReference(alwaysAsId = true)
  private Business business;

  private Integer number;
  private String description;
  private Integer numberOfSeats;


}
