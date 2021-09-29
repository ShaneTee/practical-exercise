package com.app.userservice.repository;

import com.app.userservice.components.requestBodies.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);
}
