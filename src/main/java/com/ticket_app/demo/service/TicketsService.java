package com.ticket_app.demo.service;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ticket_app.demo.exception.RecordNotFoundException;
import com.ticket_app.demo.model.TicketsEntity;
import com.ticket_app.demo.model.AgentEntity;
import com.ticket_app.demo.model.CustomerEntity;

import com.ticket_app.demo.notification.EmailService;
import com.ticket_app.demo.service.helper.StatusManager;

import com.ticket_app.demo.repository.TicketsRepository;

import com.ticket_app.demo.repository.AgentRepository;
import com.ticket_app.demo.repository.CustomerRepository;

import com.ticket_app.demo.service.AgentService;

import com.ticket_app.demo.repository.helper.TicketSpec;
import com.ticket_app.demo.repository.helper.SearchCriteria;
import com.ticket_app.demo.repository.helper.SearchOperation;


import java.util.concurrent.CompletableFuture;

@Service
public class TicketsService {
     
    @Autowired
    private TicketsRepository ticket_repository;

    @Autowired
    private CustomerRepository customer_repository;

    @Autowired
    private AgentRepository agentRepository;
     
    public List<TicketsEntity> getAllTickets(TicketsEntity entity, Integer pageNo,Integer pageSize,String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        TicketSpec ticketFilter = new TicketSpec();

        if(entity.getType() != null){
            ticketFilter.add(new SearchCriteria("type", entity.getType(), SearchOperation.MATCH));
        }

        if(entity.getStatus() != null){
            ticketFilter.add(new SearchCriteria("status", entity.getStatusasEnum(), SearchOperation.IN));
        }

        if(entity.getAssigned_agent() != null){
            AgentEntity assigned_agent = agentRepository.findById(entity.getAssigned_agent().getId()).orElse(null);
            ticketFilter.add(new SearchCriteria("assigned_agent", assigned_agent, SearchOperation.EQUAL));
        }
        


        Page<TicketsEntity> ticketsList = ticket_repository.findAll(ticketFilter,paging);
         
        if(ticketsList.hasContent()) {
        return ticketsList.getContent();
        } else {
            return new ArrayList<TicketsEntity>();
        } 
    }
     

    public TicketsEntity getTicketById(Long id) throws RecordNotFoundException
    {
        Optional<TicketsEntity> ticket = ticket_repository.findById(id);
         
        if(ticket.isPresent()) {
            return ticket.get();
        } else {
            throw new RecordNotFoundException("No ticket record exist for given id");
        }
    }

     
    public TicketsEntity createOrUpdateTicket(TicketsEntity entity) throws RecordNotFoundException
    {
        Calendar calendar = Calendar.getInstance();
        long t = calendar.getTimeInMillis();

        CustomerEntity cust = customer_repository.findById(entity.getCustomer().getId()).orElse(null);
        AgentEntity created_agent = agentRepository.findById(entity.getCreated_agent().getId()).orElse(null);
        entity.setCustomer(cust);
        entity.setCreated_agent(created_agent);
        entity.setUpdated(t);

        String status = entity.getStatus();
        if(entity.getAssigned_agent() != null){
            AgentEntity assigned_agent = agentRepository.findById(entity.getAssigned_agent().getId()).orElse(null);
            if(status == "RESOLVED"){
                int count = assigned_agent.getCount() - 1;
                assigned_agent.setCount(count);
                agentRepository.save(assigned_agent);
            }
            if(entity.getId() == null){
                int count = assigned_agent.getCount() + 1;
                assigned_agent.setCount(count);
                agentRepository.save(assigned_agent);
            }
            entity.setAssigned_agent(assigned_agent);         
        }
        else{
            Pageable paging = PageRequest.of(0, 1, Sort.by("count").ascending()); 
            Page<AgentEntity> agent = agentRepository.findAll(paging);
            for (AgentEntity agnt : agent.getContent()) {
                int count = agnt.getCount() + 1;
                agnt.setCount(count);
                agentRepository.save(agnt);
                entity.setAssigned_agent(agnt);
            }      
        }

        StatusManager status_obj = new StatusManager(status);
        status_obj.handleStatus(entity.getAssigned_agent().getName(),entity.getCustomer().getEmail());
        return ticket_repository.save(entity);
      
    }

    public void deleteTicketById(Long id) throws RecordNotFoundException
    {
        Optional<TicketsEntity> ticket = ticket_repository.findById(id);
        if(ticket.isPresent())
        {
            ticket_repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
     
}