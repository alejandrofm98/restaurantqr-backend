package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
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
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "business"}))
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Float offer;
  private Float subCategory;
  private String description;
  private Float price;
  private Integer category;
  private Integer status;
  private Timestamp publicationDate;
  private Boolean trending;

  @ManyToOne
  @JoinColumn(name = "businessUuid", referencedColumnName = "business_uuid")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "businessUuid")
  @JsonIdentityReference(alwaysAsId = true)
  private Business business;
  private String image;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private List<Ingredient> ingredients;

  @PrePersist
  protected void onCreate() {
    this.publicationDate = Timestamp.from(Instant.now());
  }
}


