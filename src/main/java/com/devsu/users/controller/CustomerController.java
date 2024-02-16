package com.devsu.users.controller;

import com.devsu.users.service.CustomerService;
import com.devsu.users.service.dto.CustomerDto;
import com.devsu.users.service.dto.request.BaseResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/authentication")
public class CustomerController {

  final CustomerService customerService;

  @PostMapping("/customer")
  public ResponseEntity<BaseResponseDto> saveCustomer(@RequestBody @Valid CustomerDto customerDto) {
    return new ResponseEntity<>(customerService.save(customerDto), HttpStatus.CREATED);
  }
}