package com.ticket_app.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket_app.demo.exception.RecordNotFoundException;
import com.ticket_app.demo.model.CustomerEntity;
import com.ticket_app.demo.repository.CustomerRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;

    public CustomerEntity createOrUpdateCustomer(CustomerEntity entity) throws RecordNotFoundException
    {
      //  Optional<TicketsEntity> ticket = repository.findById(entity.getId());
           
           System.out.println(entity);
            CustomerEntity newentity = customerRepository.save(entity);
            return newentity;
        
    }


    public List<CustomerEntity> getCustomers(Integer pageNo,Integer pageSize,String sortBy) throws RecordNotFoundException
    {
      //  Optional<TicketsEntity> ticket = repository.findById(entity.getId());
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            Page<CustomerEntity> newentity = customerRepository.findAll(paging);
            if(newentity.hasContent()) {
            return newentity.getContent();
            } else {
                return new ArrayList<CustomerEntity>();
            } 
        
    }

    public void deletecustomerById(Long id) throws RecordNotFoundException
    {
        Optional<CustomerEntity> customer = customerRepository.findById(id);
         
        if(customer.isPresent())
        {
            customerRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No customer record exist for given id");
        }
    }



     
}