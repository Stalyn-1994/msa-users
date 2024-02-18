package com.devsu.users.helper.factory;

import static com.devsu.users.helper.Helper.buildResponseEntity;

import com.devsu.users.domain.db.CustomerEntity;
import com.devsu.users.domain.exception.BadRequestException;
import com.devsu.users.repository.CustomerRepository;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import com.devsu.users.service.dto.response.CustomerResponseDto;
import com.devsu.users.service.impl.KafkaServiceImpl;
import com.devsu.users.service.mapper.CustomerServiceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerWithAccount implements CustomerInterface {

  final Environment environment;
  final ObjectMapper objectMapper;
  final KafkaServiceImpl kafkaService;
  final CustomerRepository customerRepository;
  final CustomerServiceMapper customerServiceMapper;

  @Override
  public ResponseEntity<BaseResponseDto> save(Optional<CustomerEntity> customerEntity,
      CustomerRequestDto customerRequestDto) {
    String customerId = getCustomerId(customerEntity, customerRequestDto);
    customerRequestDto.getAccount().setName(customerRequestDto.getName());
    kafkaService.send(environment.getRequiredProperty("kafka.topic"),
        getStringAccount(customerRequestDto));
    return buildResponseEntity(CustomerResponseDto.builder()
        .customerId(customerId)
        .build(), HttpStatus.CREATED);
  }

  private String getCustomerId(Optional<CustomerEntity> customerEntity,
      CustomerRequestDto customerRequestDto) {
    if (customerEntity.isEmpty()) {
      return customerRepository.save(customerServiceMapper.toCustomerEntity(customerRequestDto))
          .getClientId();
    }
    return customerEntity.get().getClientId();
  }

  private String getStringAccount(CustomerRequestDto customerRequestDto) {
    try {
      return objectMapper.writeValueAsString(customerRequestDto.getAccount());
    } catch (Exception exception) {
      throw new BadRequestException(exception.getMessage());
    }
  }
}
