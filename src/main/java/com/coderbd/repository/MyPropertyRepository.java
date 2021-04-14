package com.coderbd.repository;

import com.coderbd.entity.MyProperty;
import com.coderbd.entity.User;
import com.coderbd.enums.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 * @author Md. Rajaul Islam
 */

@Repository
public interface MyPropertyRepository extends JpaRepository<MyProperty, Long> {
    Optional<MyProperty> findBySerialNoAndUser(String serialNo, User user);
    Optional<MyProperty> findByIdAndUser(Long id, User user);

    List<MyProperty> findAllByUserAndStatus(User user, PropertyStatus status);

    List<MyProperty> findAllByUserAndRegistrationDate(User user, LocalDate date);

    List<MyProperty> findAllByStatus(PropertyStatus status);

    @Transactional
    @Modifying
    @Query("update MyProperty m set m.status=?1 where m.id=?2")
    int approveProperty(PropertyStatus status,Long id);
}
