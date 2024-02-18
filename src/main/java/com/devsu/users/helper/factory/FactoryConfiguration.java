package com.devsu.users.helper.factory;

import static com.devsu.users.helper.factory.CreateTypeEnum.ACCOUNT_CUSTOMER;
import static com.devsu.users.helper.factory.CreateTypeEnum.CUSTOMER;

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
  public Map<CreateTypeEnum, CustomerInterface> loadServices() {
    Map<CreateTypeEnum, CustomerInterface> map = new LinkedHashMap<>();
    map.put(CUSTOMER, customerWithoutAccount);
    map.put(ACCOUNT_CUSTOMER, customerWithAccount);
    return map;
  }
}