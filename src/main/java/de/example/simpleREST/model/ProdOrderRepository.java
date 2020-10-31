package de.example.simpleREST.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.format.annotation.DateTimeFormat;

public interface ProdOrderRepository extends CrudRepository<ProdOrder, UUID> {
  @Query("select e from #{#entityName} e where created between ?1 and ?2")
  List<ProdOrder> findInRange(
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime start, 
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime end);
}
