package com.devsu.users.service.impl;

import static com.devsu.users.helper.Helper.buildResponseEntity;
import static com.devsu.users.util.Constants.NOT_FOUND;

import com.devsu.users.domain.jpa.CustomerEntity;
import com.devsu.users.domain.jpa.exception.NotFoundException;
import com.devsu.users.repository.CustomerRepository;
import com.devsu.users.service.CustomerService;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.request.CustomerRequestUpdateDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import com.devsu.users.service.dto.response.CustomerResponseDto;
import com.devsu.users.service.mapper.CustomerServiceMapper;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerServiceMapper customerServiceMapper;

  @Override
  public ResponseEntity<BaseResponseDto> save(CustomerRequestDto customerDto) {
    String customerId = customerRepository.save(customerServiceMapper.toCustomerEntity(customerDto))
        .getClientId();
    return buildResponseEntity(CustomerResponseDto.builder()
        .customerId(customerId)
        .build(), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<BaseResponseDto> update(CustomerRequestUpdateDto customerDto) {
    Optional<CustomerEntity> customerEntity = customerRepository.findCustomerEntitiesByClientId(
        customerDto.getCustomerId());
    if (customerEntity.isPresent()) {
      String customerId = customerRepository.save(
          customerServiceMapper.toCustomerEntityUpdated(customerDto
              , customerEntity.get().getId())).getClientId();
      return buildResponseEntity(CustomerResponseDto.builder()
          .customerId(customerId)
          .build(), HttpStatus.OK);
    }
    throw new NotFoundException(NOT_FOUND);
  }

  @Override
  public ResponseEntity<BaseResponseDto> edit(Map<String, Object> customerDto,
      String identification) {
    Optional<CustomerEntity> customerEntity = customerRepository.findCustomerEntitiesByClientId(
        identification);
    if (customerEntity.isPresent()) {
      customerDto.forEach((key, value) -> {
        Field field = ReflectionUtils.findField(CustomerEntity.class, key);
        if (field != null) {
          field.setAccessible(true);
          ReflectionUtils.setField(field, customerEntity.get(), value);
        }
      });
      String customerId = customerRepository.save(customerEntity.get()).getClientId();
      return buildResponseEntity(CustomerResponseDto.builder()
          .customerId(customerId)
          .build(), HttpStatus.OK)
          ;
    }
    throw new NotFoundException(NOT_FOUND);
  }

  @Override
  public ResponseEntity<BaseResponseDto> delete(String identification) {
    Optional<CustomerEntity> customerEntity = customerRepository.findCustomerEntitiesByClientId(
        identification);
    if (customerEntity.isPresent()) {
      customerRepository.delete(customerEntity.get());
      return buildResponseEntity(CustomerResponseDto.builder().customerId(identification).build(),
          HttpStatus.NO_CONTENT);
    }
    throw new NotFoundException(NOT_FOUND);
  }
}