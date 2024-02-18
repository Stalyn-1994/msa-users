package com.devsu.users.helper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

  @Value("${cipher.key}")
  private String secret;

  @Override
  public String convertToDatabaseColumn(String attribute) {
    if (attribute == null) return null;
    TextEncryptor encryptor = Encryptors.text(secret, "5c0744940b5c369b");
    return encryptor.encrypt(attribute);
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    TextEncryptor encryptor = Encryptors.text(secret, "5c0744940b5c369b");
    return encryptor.decrypt(dbData);
  }
}