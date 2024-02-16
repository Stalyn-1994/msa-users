package com.devsu.users.repository.impl;

import static com.devsu.users.util.Constants.NOT_FOUND;

import com.devsu.users.domain.jpa.CustomerEntity;
import com.devsu.users.domain.jpa.exception.NotFoundException;
import com.devsu.users.repository.CustomerDao;
import com.devsu.users.repository.CustomerRepository;
import com.devsu.users.service.mapper.CustomerServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerDaoImpl implements CustomerDao {

  final CustomerRepository customerRepository;
  final CustomerServiceMapper customerServiceMapper;

  public CustomerEntity findCustomerEntitiesByClientId(String id) {
    return customerRepository.findCustomerEntitiesByClientId(
        id).orElseThrow(() -> new NotFoundException(NOT_FOUND));
  }

  public CustomerEntity save(CustomerEntity customerEntity) {
    return customerRepository.save(customerEntity);
  }

  public void delete(String id) {
    CustomerEntity customerEntity = findCustomerEntitiesByClientId(id);
    customerRepository.delete(customerEntity);
  }
}