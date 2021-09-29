package com.app.userservice.exceptions;

public class UserNotFoundException extends ClassNotFoundException{

    public static String MESSAGE = "User not found.";

    public UserNotFoundException()
    {
        super(MESSAGE);
    }

    public UserNotFoundException(String s) {
        super(MESSAGE);
    }
}
