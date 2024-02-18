package com.devsu.users.controller;

import com.devsu.users.service.CustomerService;
import com.devsu.users.service.dto.request.CustomerRequestDto;
import com.devsu.users.service.dto.request.CustomerRequestUpdateDto;
import com.devsu.users.service.dto.response.BaseResponseDto;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/customer")
public class CustomerController {

  final CustomerService customerService;

  @PostMapping("")
  public ResponseEntity<BaseResponseDto> saveCustomer(
      @RequestBody @Valid CustomerRequestDto customerDto) {
    return customerService.save(customerDto);
  }

  @PutMapping("")
  public ResponseEntity<BaseResponseDto> updateCustomer(
      @RequestBody @Valid CustomerRequestUpdateDto customerDto) {
    return customerService.update(customerDto);
  }

  @PatchMapping("/{identification}")
  public ResponseEntity<BaseResponseDto> editCustomer(
      @RequestBody Map<String, Object> customerDto, @PathVariable String identification) {
    return customerService.edit(customerDto, identification);
  }


  @DeleteMapping("/{identification}")
  public ResponseEntity<BaseResponseDto> deleteCustomer(
      @PathVariable String identification) {
    return customerService.delete(identification);
  }

  @GetMapping("/{identification}")
  public ResponseEntity<BaseResponseDto> getCustomerByCustomerId(
      @PathVariable String identification) {
    return customerService.get(identification);
  }
}