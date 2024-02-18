package com.devsu.users.helper.factory;

import static com.devsu.users.util.Constants.ALREADY_IDENTIFICATION_REGISTRATION;

import com.devsu.users.domain.db.CustomerEntity;
import com.devsu.users.domain.exception.BadRequestException;
import com.devsu.users.repository.CustomerRepository;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.impl.KafkaServiceImpl;
import com.devsu.users.service.mapper.CustomerServiceMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerWithoutAccount implements CustomerInterface {

  final CustomerRepository customerRepository;
  final CustomerServiceMapper customerServiceMapper;
  final KafkaServiceImpl kafkaService;

  @Override
  public String save(Optional<CustomerEntity> customerEntity,
      CustomerRequestDto customerRequestDto) {

    if (customerEntity.isEmpty()) {
      return customerRepository.save(
              customerServiceMapper.toCustomerEntity(customerRequestDto))
          .getClientId();
    }
    throw new BadRequestException(ALREADY_IDENTIFICATION_REGISTRATION);

  }
}