package com.devsu.users.service.dto.request;

import com.devsu.users.domain.enums.GenderEnum;
import com.devsu.users.domain.validations.GenderValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequestDto {

  @NotBlank(message = "Identification is required")
  @Size(min = 8, max = 20, message = "Identification must be between 8 and 20 characters")
  String identification;

  @NotBlank(message = "Name is required")
  @Size(max = 100, message = "Name must be less than 100 characters")
  String name;

  @NotNull(message = "Age is required")
  @Min(value = 0, message = "Age must be at least 18")
  @Max(value = 100, message = "Age must be less than 100")
  Integer age;

  @GenderValidation
  GenderEnum gender;

  String address;

  @NotBlank(message = "Cellphone is required")
  @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Cellphone must be a valid international phone number")
  String cellphone;


  @Builder.Default
  Boolean state = false;

  @NotBlank(message = "Password is required")
  @Length(min = 8, message = "Password must have at least 8 characters")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and no whitespaces")
  String password;

  @Valid
  AccountRequestDto account;
}
