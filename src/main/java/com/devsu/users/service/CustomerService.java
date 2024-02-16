package com.devsu.users.service;

import com.devsu.users.service.dto.CustomerDto;
import com.devsu.users.service.dto.request.BaseResponseDto;

public interface CustomerService {

  BaseResponseDto save(CustomerDto customerDto);
}
