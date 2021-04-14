package com.coderbd.controller;

import com.coderbd.entity.User;
import com.coderbd.repository.UserRepository;
import com.coderbd.service.MyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author Md. Rajaul Islam
 */
@Validated
@RestController
@RequestMapping(value = "/api/user/")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(UserController.class);


    private final UserRepository userRepository;
    private final MyUserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, MyUserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @implNote This method creates a user and can login later
     * @param user
     * @return
     */
    @PostMapping(value = "create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        ResponseEntity<User> responseEntity = null;
        try {
            User user1 = this.userService.save(user);
            LOG.info("Saved User Successfully");
            responseEntity = new ResponseEntity<>(user1, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            LOG.error("DataIntegrityViolationException System Error ", e.getMessage());
            throw new DataIntegrityViolationException("Duplicate Entry");
        } catch (Exception e) {
            LOG.error("System Error ", e.getMessage());
        }
        return responseEntity;
    }

    /**
     * @implNote This method displays list of all users, Only Admin should handle these
     * @return
     */

    @GetMapping(value = "list")
    public ResponseEntity<List<User>> getAllUsers() {
        LOG.info("Getting All Users");
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.FOUND);
    }

    /**
     * @implNote This method displays a User by ID, We need focus here
     * @param id
     * @return
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        LOG.info("Getting A User By ID");
        return ResponseEntity.of(userRepository.findById(id));
    }
}
