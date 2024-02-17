package com.devsu.users.domain.db;

import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GenderEnum {

  M("Masculine"), F("F");

  final String type;

  public static Optional<GenderEnum> findByCode(String name) {
    return Arrays.stream(GenderEnum.values()).filter(genderEnum -> genderEnum.name().equals(name))
        .findFirst();
  }
}
