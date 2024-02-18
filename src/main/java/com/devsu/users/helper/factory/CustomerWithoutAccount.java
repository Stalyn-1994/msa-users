package com.devsu.users.helper.factory;

import static com.devsu.users.helper.Helper.buildResponseEntity;
import static com.devsu.users.util.Constants.ALREADY_IDENTIFICATION_REGISTRATION;

import com.devsu.users.domain.db.CustomerEntity;
import com.devsu.users.domain.exception.BadRequestException;
import com.devsu.users.repository.CustomerRepository;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import com.devsu.users.service.dto.response.CustomerResponseDto;
import com.devsu.users.service.impl.KafkaServiceImpl;
import com.devsu.users.service.mapper.CustomerServiceMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerWithoutAccount implements CustomerInterface {

  final CustomerRepository customerRepository;
  final CustomerServiceMapper customerServiceMapper;
  final KafkaServiceImpl kafkaService;

  @Override
  public ResponseEntity<BaseResponseDto> save(Optional<CustomerEntity> customerEntity,
      CustomerRequestDto customerRequestDto) {

    if (customerEntity.isEmpty()) {
      String customerId = customerRepository.save(
              customerServiceMapper.toCustomerEntity(customerRequestDto))
          .getClientId();
      return buildResponseEntity(CustomerResponseDto.builder()
          .customerId(customerId)
          .build(), HttpStatus.CREATED);
    }
    throw new BadRequestException(ALREADY_IDENTIFICATION_REGISTRATION);

  }
}