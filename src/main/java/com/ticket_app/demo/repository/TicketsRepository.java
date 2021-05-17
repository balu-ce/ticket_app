package com.ticket_app.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.ticket_app.demo.model.TicketsEntity;
import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Repository
public interface TicketsRepository 
        extends PagingAndSortingRepository<TicketsEntity, Long> ,  JpaSpecificationExecutor<TicketsEntity> {
}
