package com.coderbd.service;

import com.coderbd.entity.MyProperty;
import com.coderbd.entity.User;
import java.util.List;
import java.util.Optional;
/**
 * @author Md. Rajaul Islam
 */
public interface MyPropertyService {
    User loggedInUser();
    MyProperty save(MyProperty property);
    MyProperty update(MyProperty property);
    int approveProperty(Long id);
    List<MyProperty> findAll();
    List<MyProperty> findAllByUser();
    Optional<MyProperty> findOne(Long id);
    Optional<MyProperty> findOneByUser(Long id);
}
