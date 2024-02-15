package com.devsu.users.service.impl;

import com.devsu.users.repository.UserRepository;
import com.devsu.users.service.UserService;
import com.devsu.users.service.dto.CustomerDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  @Override
  public void save(CustomerDto customerDto) {
     userRepository.save(customerDto);
  }
}