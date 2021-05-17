package com.ticket_app.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ticket_app.demo.model.CustomerEntity;
 
@Repository
public interface CustomerRepository
        extends PagingAndSortingRepository<CustomerEntity, Long> {
 
}
