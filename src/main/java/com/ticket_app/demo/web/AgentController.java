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
import com.ticket_app.demo.model.AgentEntity;
import com.ticket_app.demo.service.AgentService;

@RestController
@RequestMapping("/agent")
public class AgentController
{
    @Autowired
    AgentService service;
 

    @PostMapping
    public ResponseEntity<AgentEntity> createTicket( @RequestBody AgentEntity ticket) 
                                                    throws RecordNotFoundException{
        System.out.println("coming here");
        AgentEntity ticket_update = service.createOrUpdateAgent(ticket);
 
        return new ResponseEntity<AgentEntity>(ticket_update, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AgentEntity>> getAgents(
                                    @RequestParam(defaultValue = "1") Integer pageNo,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "id") String sortBy)
                                  throws RecordNotFoundException{

        pageNo = pageNo - 1;
        List<AgentEntity> agnets  = service.getAgent(pageNo,pageSize,sortBy);
 
        return new ResponseEntity<List<AgentEntity>>(agnets, new HttpHeaders(), HttpStatus.OK);
    }
}