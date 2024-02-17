package com.devsu.users.domain.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "users", name = "customer")
public class CustomerEntity extends PersonEntity {

  @Column(length = 50, name = "client_id", unique = true)
  private String clientId;
  @Column(length = 255)
  private String password;
  @Column(length = 50)
  private Boolean state;
}