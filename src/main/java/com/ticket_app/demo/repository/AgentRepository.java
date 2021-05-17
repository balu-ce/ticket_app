package com.ticket_app.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;

import com.ticket_app.demo.model.AgentEntity;
 
@Repository
public interface AgentRepository
        extends PagingAndSortingRepository<AgentEntity, Long> {
 
}
