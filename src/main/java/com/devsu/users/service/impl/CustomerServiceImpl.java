package com.devsu.users.service.impl;

import static com.devsu.users.helper.Helper.buildResponseEntity;
import static com.devsu.users.helper.factory.TypeEnum.ACCOUNT;
import static com.devsu.users.helper.factory.TypeEnum.CUSTOMER;
import static com.devsu.users.util.Constants.NOT_FOUND;

import com.devsu.users.domain.db.CustomerEntity;
import com.devsu.users.domain.exception.NotFoundException;
import com.devsu.users.helper.factory.CustomerInterface;
import com.devsu.users.helper.factory.TypeEnum;
import com.devsu.users.repository.CustomerRepository;
import com.devsu.users.service.CustomerService;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.request.CustomerRequestUpdateDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import com.devsu.users.service.dto.response.CustomerResponseDto;
import com.devsu.users.service.mapper.CustomerServiceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceImpl implements CustomerService {

  final CustomerRepository customerRepository;
  final CustomerServiceMapper customerServiceMapper;
  @Resource
  final Map<TypeEnum, CustomerInterface> loadServices;

  @Override
  public ResponseEntity<BaseResponseDto> save(CustomerRequestDto customerDto) {
    String customerId;
    Optional<CustomerEntity> customerEntity = customerRepository
        .findCustomerEntitiesByIdentification(customerDto.getIdentification());
    if (Objects.isNull(customerDto.getAccount())) {
      customerId = loadServices.get(CUSTOMER).save(customerEntity, customerDto);
    } else {
      customerId = loadServices.get(ACCOUNT).save(customerEntity, customerDto);
    }
    return buildResponseEntity(CustomerResponseDto.builder()
        .customerId(customerId)
        .build(), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<BaseResponseDto> update(CustomerRequestUpdateDto customerDto) {
    CustomerEntity customerEntity = customerRepository.findCustomerEntitiesByClientId(
        customerDto.getCustomerId()).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    String customerId = customerRepository.save(
            customerServiceMapper.toCustomerEntityUpdated(customerDto, customerEntity.getId()))
        .getClientId();
    return buildResponseEntity(CustomerResponseDto.builder()
        .customerId(customerId)
        .build(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto> edit(Map<String, Object> customerDto,
      String customerId) {
    CustomerEntity customerEntity = customerRepository.findCustomerEntitiesByClientId(
        customerId).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    customerDto.forEach((key, value) -> {
      Field field = ReflectionUtils.findField(CustomerEntity.class, key);
      if (field != null) {
        field.setAccessible(true);
        ReflectionUtils.setField(field, customerEntity, value);
      }
    });
    String clientId = customerRepository.save(customerEntity).getClientId();
    return buildResponseEntity(CustomerResponseDto.builder()
        .customerId(clientId)
        .build(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto> delete(String customerId) {
    CustomerEntity customerEntity = customerRepository.findCustomerEntitiesByClientId(
        customerId).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    customerRepository.delete(customerEntity);
    return ResponseEntity.noContent().build();
  }
}