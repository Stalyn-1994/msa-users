package com.devsu.users.domain.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomerEntityTest {

  public static final String CLIENT_ID_CONSTRAINT = "Expected a constraint violation for clientId";
  public static final String NULL_CLIENT_ID = "Expected a constraint violation for null clientId";
  public static final String NULL_PASSWORD = "Expected a constraint violation for null password";
  public static final String STATE_NULL_CONSTRAINT = "Expected a constraint violation for null state";
  public static final String CONSTRAINT_PASSWORD = "Expected a constraint violation for password";
  public static final String VIOLATIONS_CONSTRAINT = "Expected no constraint violations";
  public static final String CLIENT_ID = "12345";
  public static final String PASSWORD = "Pass@1234";
  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testCustomerEntityValidations() {
    CustomerEntity customer = new CustomerEntity();
    customer.setClientId(CLIENT_ID);
    customer.setPassword(PASSWORD);
    customer.setState(true);

    Set<ConstraintViolation<CustomerEntity>> violations = validator.validate(customer);
    assertTrue(violations.isEmpty(), VIOLATIONS_CONSTRAINT);

    customer.setClientId("");
    violations = validator.validateProperty(customer, "clientId");
    assertFalse(violations.isEmpty(), CLIENT_ID_CONSTRAINT);

    customer.setPassword("short");
    violations = validator.validateProperty(customer, "password");
    assertFalse(violations.isEmpty(), CONSTRAINT_PASSWORD);

    customer.setClientId(null);
    violations = validator.validateProperty(customer, "clientId");
    assertFalse(violations.isEmpty(), NULL_CLIENT_ID);

    customer.setPassword(null);
    violations = validator.validateProperty(customer, "password");
    assertFalse(violations.isEmpty(), NULL_PASSWORD);

    customer.setClientId(CLIENT_ID);
    customer.setPassword(PASSWORD);
    customer.setState(null);
    violations = validator.validateProperty(customer, "state");
    assertFalse(violations.isEmpty(), STATE_NULL_CONSTRAINT);
  }
}
