package com.devsu.users.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer", schema = "users")
public class CustomerEntity extends PersonEntity {

  @Column(length = 50, name = "client_id")
  private String clientId;
  @Column(length = 255)
  private String password;
  @Column(length = 50)
  private Boolean status;

}