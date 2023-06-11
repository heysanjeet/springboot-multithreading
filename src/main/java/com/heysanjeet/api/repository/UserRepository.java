package com.heysanjeet.api.repository;

import com.heysanjeet.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
