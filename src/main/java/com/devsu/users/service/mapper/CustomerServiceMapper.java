package com.devsu.users.service.mapper;

import com.devsu.users.domain.jpa.CustomerEntity;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.request.CustomerRequestUpdateDto;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CustomerServiceMapper {

  @Mapping(target = "name", source = "customerDto.name")
  @Mapping(target = "gender", source = "customerDto.gender")
  @Mapping(target = "age", source = "customerDto.age")
  @Mapping(target = "identification", source = "customerDto.identification")
  @Mapping(target = "address", source = "customerDto.address")
  @Mapping(target = "cellphone", source = "customerDto.cellphone")
  @Mapping(target = "password", source = "customerDto.password")
  @Mapping(target = "status", source = "customerDto.status")
  @Mapping(target = "clientId", expression = "java(generateClientId())")
  public abstract CustomerEntity toCustomerEntity(CustomerRequestDto customerDto);

  public String generateClientId() {
    return String.valueOf(UUID.randomUUID());
  }

  public CustomerEntity toCustomerEntityUpdated(CustomerRequestUpdateDto customerDto, Long id) {
    CustomerEntity customerEntity = toCustomerEntity(customerDto);
    customerEntity.setClientId(customerDto.getCustomerId());
    customerEntity.setId(id);
    return customerEntity;
  }
}