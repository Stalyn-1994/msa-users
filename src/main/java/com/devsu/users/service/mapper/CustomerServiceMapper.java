package com.devsu.users.service.mapper;

import com.devsu.users.domain.jpa.CustomerEntity;
import com.devsu.users.service.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CustomerServiceMapper {

  @Mapping(target ="name",source = "customerDto.name")
  @Mapping(target ="gender",source = "customerDto.gender")
  @Mapping(target ="age",source = "customerDto.age")
  @Mapping(target ="identification",source = "customerDto.identification")
  @Mapping(target ="address",source = "customerDto.address")
  @Mapping(target ="cellphone",source = "customerDto.cellphone")
  @Mapping(target ="password",source = "customerDto.password")
  @Mapping(target ="status",source = "customerDto.status")
  CustomerEntity toCustomerEntity(CustomerDto customerDto);
}
