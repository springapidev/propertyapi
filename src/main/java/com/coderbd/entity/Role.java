package com.coderbd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Md. Rajaul Islam
 */
@Data
@AllArgsConstructor
@Builder
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Role name can not null")
    @Column(unique = true)
    @Size(min = 3,message = "Role name at least 3 Characters!")
    private String roleName;
    @Size(min = 5,message = "Note at least 5 Characters!")
    private String note;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role() {
    }
}
