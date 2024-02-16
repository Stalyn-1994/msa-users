package com.devsu.users.controller;

import com.devsu.users.service.CustomerService;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.request.CustomerRequestUpdateDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/authentication")
public class CustomerController {

  final CustomerService customerService;

  @PutMapping("/customer")
  public ResponseEntity<BaseResponseDto> updateCustomer(
      @RequestBody @Valid CustomerRequestUpdateDto customerDto) {
    return new ResponseEntity<>(customerService.update(customerDto), HttpStatus.OK);
  }

  @PatchMapping("/customer/{identification}")
  public ResponseEntity<BaseResponseDto> editCustomer(
      @RequestBody Map<String, Object> customerDto, @PathVariable String identification) {
    return new ResponseEntity<>(customerService.edit(customerDto,identification), HttpStatus.OK);
  }

  @PostMapping("/customer")
  public ResponseEntity<BaseResponseDto> saveCustomer(
      @RequestBody @Valid CustomerRequestDto customerDto) {
    return new ResponseEntity<>(customerService.save(customerDto), HttpStatus.CREATED);
  }
}