package com.devsu.users.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer", schema = "users")
public class CustomerEntity extends PersonEntity {

  @Column(length = 255)
  private String password;
  @Column(length = 50)
  private Boolean status;

}