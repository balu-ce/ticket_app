package com.ticket_app.demo.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ticket_app.demo.exception.RecordNotFoundException;
import com.ticket_app.demo.model.CustomerEntity;
import com.ticket_app.demo.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController
{
    @Autowired
    CustomerService service;
 
    @PostMapping
    public ResponseEntity<CustomerEntity> createCustomer( @RequestBody CustomerEntity ticket) 
                                                    throws RecordNotFoundException{
        System.out.println("coming here");
        CustomerEntity ticket_update = service.createOrUpdateCustomer(ticket);
 
        return new ResponseEntity<CustomerEntity>(ticket_update, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getCustomers(
                    @RequestParam(defaultValue = "1") Integer pageNo,
                    @RequestParam(defaultValue = "10") Integer pageSize,
                    @RequestParam(defaultValue = "id") String sortBy) 
                                                    throws RecordNotFoundException{
        List<CustomerEntity> customers = service.getCustomers(pageNo-1,pageSize,sortBy);
 
        return new ResponseEntity<List<CustomerEntity>>(customers, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletecustomerById(@PathVariable("id") Long id)
                                                    throws RecordNotFoundException {
        service.deletecustomerById(id);
        return HttpStatus.FORBIDDEN;
    }
    

}