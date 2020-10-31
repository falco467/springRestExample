package de.example.simpleREST.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProdOrder {
  @Id
  @GeneratedValue
  UUID id;

  String email;

  @CreatedDate
  LocalDateTime created;
  
  @ManyToMany
  List<Product> products;

  /**
   * @return The sum of the prices of all products in this order
   */
  public BigDecimal getTotalAmount() {
    return this.products.stream().map(Product::getPrice)
      .reduce(BigDecimal.ZERO, 
        (a,b) -> a.add(b), 
        (a,b) -> a.add(b));
  }
}