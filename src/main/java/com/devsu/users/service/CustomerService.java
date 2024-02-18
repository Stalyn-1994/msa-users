package com.devsu.users.service;

import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.request.CustomerRequestUpdateDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

  ResponseEntity<BaseResponseDto> save(CustomerRequestDto customerDto);

  ResponseEntity<BaseResponseDto> update(CustomerRequestUpdateDto customerDto);

  ResponseEntity<BaseResponseDto> edit(Map<String, Object> customerDto, String identification);

  ResponseEntity<BaseResponseDto> delete(String identification);
  ResponseEntity<BaseResponseDto> get(String identification);
}
