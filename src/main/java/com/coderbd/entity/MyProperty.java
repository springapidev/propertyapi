package com.coderbd.entity;

import com.coderbd.enums.PropertyStatus;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


/**
 * @author Md. Rajaul Islam
 */
@Data
@Entity
public class MyProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title Can not be Empty")
    private String title;
    @Column(unique = true)
    private String serialNo;
    private String description;
    private double price;
    private PropertyStatus status;
    private String photoUrl;
    private String lat;
    private String lon;
    private String owner;
    private String country;
    private LocalDate registrationDate;

    /**
     * @implNote To add some propery, everyone must be registered first and a property
     * must have a owner.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

}
