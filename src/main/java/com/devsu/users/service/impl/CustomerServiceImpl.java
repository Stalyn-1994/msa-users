package com.devsu.users.service.impl;

import static com.devsu.users.helper.Helper.buildResponseEntity;
import static com.devsu.users.util.Constants.ALREADY_IDENTIFICATION_REGISTRATION;
import static com.devsu.users.util.Constants.NOT_FOUND;

import com.devsu.users.domain.db.CustomerEntity;
import com.devsu.users.domain.exception.BadRequestException;
import com.devsu.users.domain.exception.NotFoundException;
import com.devsu.users.repository.CustomerRepository;
import com.devsu.users.service.CustomerService;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.request.CustomerRequestUpdateDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import com.devsu.users.service.dto.response.CustomerInfoResponseDto;
import com.devsu.users.service.dto.response.CustomerResponseDto;
import com.devsu.users.service.mapper.CustomerServiceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerServiceMapper customerServiceMapper;
  private final KafkaServiceImpl kafkaService;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public ResponseEntity<BaseResponseDto> save(CustomerRequestDto customerDto) {
    Optional<CustomerEntity> customerEntity = customerRepository.findCustomerEntitiesByIdentification(
        customerDto.getIdentification());
    if (Objects.isNull(customerDto.getAccount())) {
      if (customerEntity.isEmpty()) {
        String customerId = customerRepository.save(
                customerServiceMapper.toCustomerEntity(customerDto))
            .getClientId();
        return buildResponseEntity(CustomerResponseDto.builder()
            .customerId(customerId)
            .build(), HttpStatus.CREATED);
      }
      throw new BadRequestException(ALREADY_IDENTIFICATION_REGISTRATION);
    }
    String customerId;
    try {
      if (customerEntity.isEmpty()) {
        customerId = customerRepository.save(
            customerServiceMapper.toCustomerEntity(customerDto)).getClientId();
      } else {
        customerId = customerEntity.get().getClientId();
      }
      customerDto.getAccount().setName(customerDto.getName());
      kafkaService.send("example", objectMapper.writeValueAsString(customerDto.getAccount()));
      return buildResponseEntity(CustomerResponseDto.builder()
          .customerId(customerId)
          .build(), HttpStatus.CREATED);
    } catch (Exception exception) {
      throw new BadRequestException(ALREADY_IDENTIFICATION_REGISTRATION);
    }
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

  @Override
  public ResponseEntity<BaseResponseDto> get(String customerId) {
    CustomerEntity customerEntity = customerRepository.findCustomerEntitiesByClientId(
        customerId).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    CustomerInfoResponseDto customerResponseDto = customerServiceMapper.toCustomerInfoResponseDto(
        customerEntity);
    return buildResponseEntity(customerResponseDto, HttpStatus.OK);
  }
}