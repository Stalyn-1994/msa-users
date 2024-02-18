package com.devsu.users.helper.factory;

import com.devsu.users.domain.db.CustomerEntity;
import com.devsu.users.domain.exception.BadRequestException;
import com.devsu.users.repository.CustomerRepository;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.impl.KafkaServiceImpl;
import com.devsu.users.service.mapper.CustomerServiceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerWithAccount implements CustomerInterface {

  final CustomerServiceMapper customerServiceMapper;
  final CustomerRepository customerRepository;
  final KafkaServiceImpl kafkaService;
  final ObjectMapper objectMapper;

  @Override
  public String save(Optional<CustomerEntity> customerEntity,
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
      kafkaService.send("example",
          objectMapper.writeValueAsString(customerRequestDto.getAccount()));
    } catch (Exception exception) {
      throw new BadRequestException(exception.getMessage());
    }
    return customerId;
  }
}
