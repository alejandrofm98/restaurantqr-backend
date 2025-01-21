package com.example.demo.features.order.order;


import com.example.demo.features.order.orderLine.OrderLine;
import com.example.demo.features.tables.Tables;
import com.example.demo.features.business.Business;
import com.example.demo.features.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
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
@Table(name = "orders", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"order_number", "business_uuid"})})
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long orderNumber;

  @ManyToOne(targetEntity = Business.class)
  @JoinColumn(name = "business_uuid", referencedColumnName = "business_uuid")
  private Business business;

  @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss", timezone = "Europe/Madrid")
  @Builder.Default
  private LocalDateTime startDate = LocalDateTime.now();
  @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss", timezone = "Europe/Madrid")
  private LocalDateTime endDate;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<OrderLine> orderLines;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="waiter_id", referencedColumnName = "id")
  private User waiter;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "table_id", referencedColumnName = "id")
  private Tables table;

}
