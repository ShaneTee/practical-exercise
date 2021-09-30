package com.app.userservice.services;

import com.app.userservice.components.requestBodies.User;
import com.app.userservice.exceptions.UserNotFoundException;
import com.app.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
public class UserManager implements CustomUserManager {

    private final UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user){
        this.userRepository.save(user);
    }

    @Override
    public User get(String email) throws UserNotFoundException {
        Optional<User> user = this.userRepository.findFirstByEmail(email);

        if(user.isEmpty())
        {
            throw new UserNotFoundException();
        }

        return user.get();
    }

    @Override
    public void delete(String email) throws UserNotFoundException{
        User user = this.get(email);

        this.userRepository.delete(user);
    }

    @Override
    public void update(String email, User user) throws UserNotFoundException {
        User existingUser = this.get(user.getEmail());

        user.setId(existingUser.getId());

        this.userRepository.save(user);
    }
}
