package com.devsu.users.service.mapper;

import com.devsu.users.domain.jpa.CustomerEntity;
import com.devsu.users.service.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CustomerServiceMapper {

  CustomerEntity toCustomerEntity(CustomerDto customerDto);
}
