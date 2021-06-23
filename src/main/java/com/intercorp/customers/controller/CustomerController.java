package com.intercorp.customers.controller;

import com.intercorp.customers.dto.CustomerDTO;
import com.intercorp.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;*/


@RestController
@RequestMapping(value="/customers")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping(value="/hello")
    public String hello(){
        return "Hello World";
    }

    @GetMapping(value = "/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @GetMapping(value = "/list-entity/{id}")
    public ResponseEntity listEntity(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.listEntity(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity add(@RequestBody CustomerDTO customer){
        return new ResponseEntity(service.add(customer), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity edit(@PathVariable(value = "id") String id, @RequestBody CustomerDTO customer){
        return new ResponseEntity(service.edit(id,customer), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        return  new ResponseEntity(service.delete(id), HttpStatus.OK);
    }

}
