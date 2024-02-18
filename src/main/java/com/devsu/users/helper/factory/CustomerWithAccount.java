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
    String customerId;
    if (customerEntity.isEmpty()) {
      customerId = customerRepository.save(
          customerServiceMapper.toCustomerEntity(customerRequestDto)).getClientId();
    } else {
      customerId = customerEntity.get().getClientId();
    }
    customerRequestDto.getAccount().setName(customerRequestDto.getName());
    try {
      kafkaService.send(environment.getRequiredProperty("kafka.topic"),
          objectMapper.writeValueAsString(customerRequestDto.getAccount()));
    } catch (Exception exception) {
      throw new BadRequestException(exception.getMessage());
    }
    return buildResponseEntity(CustomerResponseDto.builder()
        .customerId(customerId)
        .build(), HttpStatus.CREATED);
  }
}
