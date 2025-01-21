package com.example.demo.features.order.orderLine;

import com.example.demo.features.order.order.Order;
import com.example.demo.features.order.orderLineIngredient.OrderLineIngredient;
import com.example.demo.features.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

@Audited
@Data
@ToString(exclude = {"order"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders_line", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"orderId", "lineNumber"})
})
public class OrderLine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private Long lineNumber;
  @Column(nullable = false)
  private Integer quantity;
  private String observations;

  @ManyToOne(cascade = CascadeType.ALL, targetEntity = Order.class)
  @JoinColumn(name = "order_id", referencedColumnName = "id",nullable = false)
  private Order order;

  @ManyToOne(targetEntity = Product.class)
  @JoinColumn(name = "productId", referencedColumnName = "id", nullable = false)
  @JsonBackReference
  private Product product;

  @OneToMany(mappedBy = "orderLine")
  @JsonManagedReference
  private List<OrderLineIngredient> orderLineIngredients;
}
