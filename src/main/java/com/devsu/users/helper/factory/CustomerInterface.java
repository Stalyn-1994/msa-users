package com.devsu.users.helper.factory;

import com.devsu.users.domain.db.CustomerEntity;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import java.util.Optional;

public interface CustomerInterface {

  String save(Optional<CustomerEntity> customerEntity, CustomerRequestDto customerRequestDto);

}
