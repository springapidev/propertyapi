package com.coderbd.controller;

import com.coderbd.repository.RoleRepository;
import com.coderbd.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
/**
 * @author Md. Rajaul Islam
 */
@RestController
@RequestMapping(value = "/role/")
public class RolesController {
    private final Logger LOG = LoggerFactory.getLogger(RolesController.class);


    private final RoleRepository roleRepository;

    public RolesController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * @implNote This method saves ROLE, but ADMIN should do this only, We need focus here
     * @param role
     * @return
     */
    @PostMapping(value = "add")
    public ResponseEntity<?> add(@Valid @RequestBody Role role) {
        ResponseEntity responseEntity = null;
       try{
            role.setRoleName(role.getRoleName().toUpperCase());
            Role role1 = this.roleRepository.save(role);
            LOG.info("Saved Role Successfully");
            responseEntity = new ResponseEntity<>(role1,HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            LOG.error("DataIntegrityViolationException System Error ", e.getMessage());
            throw new DataIntegrityViolationException("Duplicate Entry");
        } catch (Exception e) {
            LOG.error("System Error ", e.getMessage());
        }

        return responseEntity;
    }

    /**
     * @implNote This method displays list of all roles, We need focus here
     * @return
     */

    @GetMapping(value = "list")
    public ResponseEntity<List<Role>> getAllRoles() {
        LOG.info("Getting All Roles");
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.FOUND);
    }

    /**
     * @implNote This method displays a role by ID, We need focus here
     * @param roleId
     * @return
     */
    @GetMapping(value = "{roleId}")
    public ResponseEntity<Optional<Role>> getAllRoles(@PathVariable("roleId") Long roleId) {
        LOG.info("Getting A Role By ID");
        return new ResponseEntity(roleRepository.findById(roleId), HttpStatus.FOUND);
    }
}
