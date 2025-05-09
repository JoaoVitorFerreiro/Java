package com.example.AulaTeste.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AulaTeste.model.UserModel;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
  UserModel findByEmail(String email);
  void deleteByEmail(String email);
}
