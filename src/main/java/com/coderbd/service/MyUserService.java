package com.coderbd.service;


import com.coderbd.entity.User;

import java.util.Optional;
/**
 * @author Md. Rajaul Islam
 */
public interface MyUserService {
    User save(User user);
    Optional<User> findByUsername(String username);
}
