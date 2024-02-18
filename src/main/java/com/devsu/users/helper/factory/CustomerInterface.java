package com.devsu.users.helper.factory;

import com.devsu.users.domain.db.CustomerEntity;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public interface CustomerInterface {

  ResponseEntity<BaseResponseDto>  save(Optional<CustomerEntity> customerEntity, CustomerRequestDto customerRequestDto);

}
