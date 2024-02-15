package com.devsu.users.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer", schema = "users")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(nullable = false, length = 255)
  private String name;
  @Column(length = 50)
  private String gender;
  private Integer age;
  @Column(length = 100)
  private String identification;
  @Column(length = 255)
  private String address;
  @Column(length = 50)
  private String cellphone;
  @Column(length = 255)
  private String password;
  @Column(length = 50)
  private Boolean status;

}
