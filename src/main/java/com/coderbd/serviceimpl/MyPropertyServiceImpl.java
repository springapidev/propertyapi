package com.coderbd.serviceimpl;

import com.coderbd.entity.MyProperty;
import com.coderbd.entity.User;
import com.coderbd.enums.PropertyStatus;
import com.coderbd.repository.MyPropertyRepository;
import com.coderbd.service.MyPropertyService;
import com.coderbd.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * @author Md. Rajaul Islam
 */

@Service
public class MyPropertyServiceImpl implements MyPropertyService {
    @Autowired
    private MyPropertyRepository repository;
    @Autowired
    private MyUserService userService;

    public User loggedInUser() {
        return this.userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    }

    @Override
    public MyProperty save(MyProperty property) {
        property.setSerialNo(UUID.randomUUID().toString());
        property.setUser(loggedInUser());
        property.setRegistrationDate(LocalDate.now());
        return this.repository.save(property);
    }

    @Override
    public MyProperty update(MyProperty property) {
        try {
            if (loggedInUser().equals(property.getUser()) && property.getId() != null) {
                return this.repository.save(property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MyProperty();
    }

    /**
     * @implNote This method checks first, user is admin or not, if admin, accept , otherwise not
     * @param id
     * @return
     */
    @Override
    public int approveProperty(Long id) {
        if(loggedInUser().getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals("ADMIN"))) {
            return this.repository.approveProperty(PropertyStatus.ACTIVE, id);
        }
        return 0;
    }


    @Override
    public List<MyProperty> findAll() {
        return this.repository.findAll();
    }

    @Override
    public List<MyProperty> findAllByUser() {
        return this.repository.findAllByUserAndStatus(loggedInUser(), PropertyStatus.ACTIVE);
    }

    @Override
    public Optional<MyProperty> findOne(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Optional<MyProperty> findOneByUser(Long id) {
        return this.repository.findByIdAndUser(id, loggedInUser());
    }
}
