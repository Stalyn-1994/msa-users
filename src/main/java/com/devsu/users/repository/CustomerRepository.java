package com.devsu.users.repository;

import com.devsu.users.domain.jpa.CustomerEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

  Optional<CustomerEntity> findCustomerEntitiesByClientId(String id);
}
