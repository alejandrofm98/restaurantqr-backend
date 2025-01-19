package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"orderLine"})
@Entity
@Table(name = "order_line_ingredient", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ingredientId", "orderLineId"})
})
public class OrderLineIngredient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ingredientId",referencedColumnName = "id")
  @JsonBackReference
  private Ingredient ingredient;

  @ManyToOne(targetEntity = OrderLine.class)
  @JoinColumn(name="orderLineId" ,referencedColumnName = "id")
  @JsonBackReference
  private OrderLine orderLine;

  private int quantity;
}
