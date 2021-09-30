package com.app.userservice.controller;

import com.app.userservice.components.requestBodies.User;
import com.app.userservice.services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserManager userManager;

    @Autowired
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody User user){
        this.userManager.create(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> retrieve(@PathVariable @Email String email) throws Exception{
        User existingUser = this.userManager.get(email);

        return ResponseEntity.ok().body(existingUser);
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updateUser(@PathVariable @Email String email, @Valid @RequestBody User user) throws ClassNotFoundException {
        this.userManager.update(email, user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable @Email String email) throws ClassNotFoundException {
        this.userManager.delete(email);

        return ResponseEntity.noContent().build();
    }

}
