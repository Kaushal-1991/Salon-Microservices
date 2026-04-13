package com.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salon.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
