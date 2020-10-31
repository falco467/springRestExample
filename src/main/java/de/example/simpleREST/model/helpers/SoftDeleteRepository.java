package de.example.simpleREST.model.helpers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * A drop-in replacement for CrudRepository, which will handle soft delete.
 * Instead of removing an item, all delete operations will set the mandatory
 * attribute "deleted" to true.
 * The default Find-methods will only show items where deleted is false.
 * The additional method "findDeleted"can be used to show deleted entries,
 * which can be addressed as normal with update operations.
 */
@NoRepositoryBean
public interface SoftDeleteRepository<T> extends CrudRepository<T, UUID> {
  @Override
  @Query("select e from #{#entityName} e where e.deleted = false")
  List<T> findAll();

  @Override
  @Query("select e from #{#entityName} e where e.id in ?1 and e.deleted = false")
  Iterable<T> findAllById(Iterable<UUID> ids);

  @Override
  @Query("select e from #{#entityName} e where e.id = ?1 and e.deleted = false")
  Optional<T> findById(UUID id);

  @Override
  @Query("select count(e) from #{#entityName} e where e.deleted = false")
  long count();

  @Override
  default boolean existsById(UUID id) {
    return findById(id).isPresent();
  }

  @Override
  @Modifying
  @Query("update #{#entityName} e set e.deleted=true where e.id = ?1")
  void deleteById(UUID id);

  @Override
  @Modifying
  @Query("update #{#entityName} e set e.deleted=true where e = ?1")
  void delete(T entity);

  @RestResource(exported=false) // To avoid naming conflict of two endpoints
  @Override
  @Modifying
  @Query("update #{#entityName} e set e.deleted=true where e in ?1")
  void deleteAll(Iterable<? extends T> entities);

  @Override
  @Modifying
  @Query("update #{#entityName} e set e.deleted=true")
  void deleteAll();

  /**
   * @return a list of all deleted entities
   */
  @Query("select e from #{#entityName} e where e.deleted = true")
  @Transactional(readOnly = true)
  List<T> findDeleted();
}
