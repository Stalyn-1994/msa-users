package com.devsu.users.service.impl;

import static com.devsu.users.helper.Helper.buildResponseEntity;

import com.devsu.users.domain.jpa.CustomerEntity;
import com.devsu.users.repository.CustomerDao;
import com.devsu.users.service.CustomerService;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.request.CustomerRequestUpdateDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import com.devsu.users.service.dto.response.CustomerResponseDto;
import com.devsu.users.service.mapper.CustomerServiceMapper;
import jakarta.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerDao customerDao;
  private final CustomerServiceMapper customerServiceMapper;

  @Override
  public ResponseEntity<BaseResponseDto> save(CustomerRequestDto customerDto) {
    String customerId = customerDao.save(customerServiceMapper.toCustomerEntity(customerDto))
        .getClientId();
    return buildResponseEntity(CustomerResponseDto.builder()
        .customerId(customerId)
        .build(), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<BaseResponseDto> update(CustomerRequestUpdateDto customerDto) {
    CustomerEntity customerEntity = customerDao.findCustomerEntitiesByClientId(
        customerDto.getCustomerId());
    String customerId = customerDao.save(
            customerServiceMapper.toCustomerEntityUpdated(customerDto, customerEntity.getId()))
        .getClientId();
    return buildResponseEntity(CustomerResponseDto.builder()
        .customerId(customerId)
        .build(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto> edit(Map<String, Object> customerDto,
      String identification) {
    CustomerEntity customerEntity = customerDao.findCustomerEntitiesByClientId(identification);
    customerDto.forEach((key, value) -> {
      Field field = ReflectionUtils.findField(CustomerEntity.class, key);
      if (field != null) {
        field.setAccessible(true);
        ReflectionUtils.setField(field, customerEntity, value);
      }
    });
    String customerId = customerDao.save(customerEntity).getClientId();
    return buildResponseEntity(CustomerResponseDto.builder()
        .customerId(customerId)
        .build(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto> delete(String identification) {
    customerDao.delete(identification);
    return ResponseEntity.noContent().build();

  }
}