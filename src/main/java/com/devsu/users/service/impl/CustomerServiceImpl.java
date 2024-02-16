package com.devsu.users.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerServiceMapper customerServiceMapper;

  @Override
  public BaseResponseDto save(CustomerRequestDto customerDto) {
    String customerId = customerRepository.save(customerServiceMapper.toCustomerEntity(customerDto))
        .getClientId();
    return BaseResponseDto.builder()
        .code(HttpStatus.CREATED.value())
        .status(HttpStatus.CREATED.getReasonPhrase())
        .data(CustomerResponseDto.builder()
            .customerId(customerId)
            .build())
        .build();
  }

  @Override
  public BaseResponseDto update(CustomerRequestUpdateDto customerDto) {
    Optional<CustomerEntity> customerEntity = customerRepository.findCustomerEntitiesByClientId(
        customerDto.getCustomerId());
    if (customerEntity.isPresent()) {
      String customerId = customerRepository.save(
          customerServiceMapper.toCustomerEntityUpdated(customerDto
              , customerEntity.get().getId())).getClientId();
      return BaseResponseDto.builder()
          .code(HttpStatus.OK.value())
          .status(HttpStatus.OK.getReasonPhrase())
          .data(CustomerResponseDto.builder()
              .customerId(customerId)
              .build())
          .build();
    }
    throw new NotFoundException();
  }

  @Override
  public BaseResponseDto edit(Map<String, Object> customerDto, String identification) {
    Optional<CustomerEntity> customerEntity = customerRepository.findCustomerEntitiesByClientId(
        identification);
    if (customerEntity.isPresent()) {
      customerDto.forEach((key, value) -> {
        Field field = ReflectionUtils.findField(CustomerEntity.class, key);
        if (field != null) {
          field.setAccessible(true);
          ReflectionUtils.setField(field, customerEntity.get()  , value);
        }
      });
      String customerId = customerRepository.save(customerEntity.get()).getClientId();
      return BaseResponseDto.builder()
          .code(HttpStatus.OK.value())
          .status(HttpStatus.OK.getReasonPhrase())
          .data(CustomerResponseDto.builder()
              .customerId(customerId)
              .build())
          .build();
    }
    throw new NotFoundException();
  }

  @Override
  public BaseResponseDto delete(CustomerRequestDto customerDto) {
    return null;
  }
}