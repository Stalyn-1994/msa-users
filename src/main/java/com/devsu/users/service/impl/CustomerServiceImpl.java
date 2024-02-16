package com.devsu.users.service.impl;

import com.devsu.users.repository.CustomerRepository;
import com.devsu.users.service.CustomerService;
import com.devsu.users.service.dto.CustomerDto;
import com.devsu.users.service.dto.request.BaseResponseDto;
import com.devsu.users.service.mapper.CustomerServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerServiceMapper customerServiceMapper;

  @Override
  public BaseResponseDto save(CustomerDto customerDto) {
    customerRepository.save(customerServiceMapper.toCustomerEntity(customerDto));
    return BaseResponseDto.builder()
        .code(HttpStatus.CREATED.value())
        .status(HttpStatus.CREATED.getReasonPhrase())
        .data(customerDto)
        .build();
  }
}