package com.devsu.users.domain.jpa.validations;

import com.devsu.users.domain.jpa.GenderEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class GenderValidator implements ConstraintValidator<GenderValidation, GenderEnum> {


  @Override
  public void initialize(GenderValidation constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(GenderEnum value, ConstraintValidatorContext context) {
    return GenderEnum.findByCode(value.name()).isPresent();
  }
}
