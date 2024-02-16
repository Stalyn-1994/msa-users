package com.devsu.users.repository;

import com.devsu.users.domain.jpa.CustomerEntity;

public interface CustomerDao {

  CustomerEntity findCustomerEntitiesByClientId(String id);

  CustomerEntity save(CustomerEntity customerEntity);

  void delete(String id);

}
