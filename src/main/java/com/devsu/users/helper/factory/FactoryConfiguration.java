package com.devsu.users.helper.factory;

import static com.devsu.users.helper.factory.TypeEnum.ACCOUNT;
import static com.devsu.users.helper.factory.TypeEnum.CUSTOMER;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FactoryConfiguration {

  final CustomerWithAccount customerWithAccount;
  final CustomerWithoutAccount customerWithoutAccount;

  @Bean
  public Map<TypeEnum, CustomerInterface> loadServices() {
    Map<TypeEnum, CustomerInterface> map = new LinkedHashMap<>();
    map.put(CUSTOMER, customerWithoutAccount);
    map.put(ACCOUNT, customerWithAccount);
    return map;
  }
}