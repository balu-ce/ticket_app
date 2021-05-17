package com.ticket_app.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ticket_app.demo.exception.RecordNotFoundException;
import com.ticket_app.demo.model.CustomerEntity;
import com.ticket_app.demo.model.AgentEntity;
import com.ticket_app.demo.repository.CustomerRepository;
import com.ticket_app.demo.repository.AgentRepository;
 
@Service
public class AgentService {
     
    @Autowired
    AgentRepository repository;


    public AgentEntity createOrUpdateAgent(AgentEntity entity) throws RecordNotFoundException
    {
      //  Optional<TicketsEntity> ticket = repository.findById(entity.getId());
            entity = repository.save(entity);
            return entity;
        
    }

    public List<AgentEntity> getAgent(Integer pageNo,Integer pageSize,String sortBy) throws RecordNotFoundException
    {
           Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
           Page<AgentEntity> agents = repository.findAll(paging);
           System.out.println(agents);
           if(agents.hasContent()) {
            return agents.getContent();
            } else {
                return new ArrayList<AgentEntity>();
            }        
    }
     
     
}