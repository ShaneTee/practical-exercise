package com.app.userservice.services;

import com.app.userservice.components.requestBodies.User;
import com.app.userservice.exceptions.UserNotFoundException;

public interface UserManagerInterface {
    public void create(User user);

    public User get(String email) throws UserNotFoundException;

    public void delete(String email) throws UserNotFoundException;

    public void update(String email, User user) throws UserNotFoundException;

}
