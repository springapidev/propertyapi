package com.coderbd.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Md. Rajaul Islam
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username Can not be Empty")
    @Column(unique = true)
    private String username;

    @Size(min = 8, message = "Password Minimum length is 8 Characters")
    private String password;

    @NotNull(message = "Email Can not be Empty")
    @Column(unique = true)
    private String email;

    private String fullName;
    private String photoUrl;
    private String country;
    private String lat;
    private String lon;
    private String ip;
    private String mac;
    private String browser;
    private String device;
    private String flagUrl;
    private LocalDate registrationDate;
    @CreatedDate
    private LocalDateTime lastUpdateDateTime;
    /**
     * @implNote One User may have multiple roles
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

}
