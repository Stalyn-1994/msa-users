package com.devsu.users.repository;

import com.devsu.users.domain.jpa.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<CustomerEntity, Long> {

}
