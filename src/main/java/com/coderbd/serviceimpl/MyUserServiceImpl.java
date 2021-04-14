package com.coderbd.serviceimpl;

import com.coderbd.entity.User;
import com.coderbd.repository.RoleRepository;
import com.coderbd.repository.UserRepository;
import com.coderbd.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Md. Rajaul Islam
 */
@Service
public class MyUserServiceImpl implements MyUserService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(this.roleRepository.findByRoleName("ADMIN").get()));
       user.setRegistrationDate(LocalDate.now());
        this.userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
