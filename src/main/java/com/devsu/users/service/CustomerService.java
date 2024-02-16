package com.devsu.users.service;

import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.request.CustomerRequestUpdateDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import java.util.Map;

public interface CustomerService {

  BaseResponseDto save(CustomerRequestDto customerDto);

  BaseResponseDto update(CustomerRequestUpdateDto customerDto);

  BaseResponseDto edit(Map<String, Object> customerDto, String identification);

  BaseResponseDto delete(String identification);
}
