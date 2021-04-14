package com.coderbd.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import com.coderbd.entity.MyProperty;
import com.coderbd.entity.User;
import com.coderbd.service.MyPropertyService;
import org.hibernate.annotations.common.reflection.XMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
/**
 * @author Md. Rajaul Islam
 */
@Validated
@RestController
@RequestMapping(value = "/api/property")
public class MyPropertyController {
    private final Logger LOG = LoggerFactory.getLogger(MyPropertyController.class);


    @Autowired
    private MyPropertyService service;

    /**
     * @implNote This method is for Property Saving, It May also control validation
     * @param myProperty
     * @param result
     * @return
     */

    @PostMapping(value = "/add")
    public ResponseEntity<?> save(@Valid @RequestBody MyProperty myProperty, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            MyProperty myProperty1 = this.service.save(myProperty);
            LOG.info("Saved MyProperty Successfully");
            responseEntity = new ResponseEntity<>(myProperty1, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            LOG.error("DataIntegrityViolationException System Error ", e.getMessage());
            throw new DataIntegrityViolationException("Duplicate Entry");
        } catch (Exception e) {
            LOG.error("System Error ", e.getMessage());
        }
        return responseEntity;
    }

    /**
     * @implNote This method is for Property update By ID, but it takes body Property, It May also control validation
     * @param myProperty
     * @param result
     * @return
     */
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@Valid @RequestBody MyProperty myProperty, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            if (myProperty.getId() != null) {
                MyProperty myProperty1 = this.service.save(myProperty);
                LOG.info("Updated MyProperty Successfully");
                responseEntity = new ResponseEntity<>(myProperty1, HttpStatus.ACCEPTED);
            } else {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }


        } catch (DataIntegrityViolationException e) {
            LOG.error("DataIntegrityViolationException System Error ", e.getMessage());
            throw new DataIntegrityViolationException("Duplicate Entry");
        } catch (Exception e) {
            LOG.error("System Error ", e.getMessage());
        }
        return responseEntity;
    }

    /**
     * @implNote This method is for activating a property and only ADMIN access can do this     * @param id
     * @return
     */
    @Transactional
    @PostMapping(value = "/approved/{id}")
    public ResponseEntity approvePropertyStatus(@PathVariable("id") Long id) {
        User user = this.service.loggedInUser();
        LOG.info("Approve Property Status By Admin only");
        int status = this.service.approveProperty(id);
        if (status > 0) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    /**
     * @implNote This method displays list of all property, but secured
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseEntity<List<MyProperty>> getAll() {
        LOG.info("Getting All Users");
        return new ResponseEntity<>(service.findAll(), HttpStatus.FOUND);
    }

    /**
     * @implNote This method displays list of all property for loggedIn users own Property, but secured
     * @return
     */
    @GetMapping(value = "/user/list")
    public ResponseEntity<List<MyProperty>> getAllByUser() {
        LOG.info("Getting All MyProperty By User");
        return new ResponseEntity<>(service.findAllByUser(), HttpStatus.FOUND);
    }

    /**
     * @implNote This method displays A single property from any, but secured
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<MyProperty>> getOne(@PathVariable("id") Long id) {
        LOG.info("Getting A Property By ID");
        return new ResponseEntity(service.findOne(id), HttpStatus.FOUND);
    }

    /**
     * @implNote This method displays a single property for loggedIn users own Property, but secured
     * @param id
     * @return
     */
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Optional<MyProperty>> getOneByUser(@PathVariable("id") Long id) {
        LOG.info("Getting A Property By ID");
        return new ResponseEntity(service.findOneByUser(id), HttpStatus.FOUND);
    }

}
