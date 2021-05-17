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
import com.ticket_app.demo.model.TicketsEntity;
import com.ticket_app.demo.service.TicketsService;
 
@RestController
@RequestMapping("/tickets")
public class TicketsController
{
    @Autowired
    TicketsService service;
 
    @PostMapping("/search")
    public ResponseEntity<List<TicketsEntity>> getAllTickets(
                        @RequestBody TicketsEntity ticket,
                        @RequestParam(defaultValue = "1") Integer pageNo,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "id") String sortBy) 
        {
        List<TicketsEntity> list = service.getAllTickets(ticket,pageNo-1,pageSize,sortBy);
 
        return new ResponseEntity<List<TicketsEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes={"application/json"})
    public ResponseEntity<TicketsEntity> createTicket(@Valid @RequestBody TicketsEntity ticket) 
                                                    throws RecordNotFoundException{
        System.out.println(ticket);
        TicketsEntity ticket_update = service.createOrUpdateTicket(ticket);
 
        return new ResponseEntity<TicketsEntity>(ticket_update, new HttpHeaders(), HttpStatus.OK);
    }

     @GetMapping("/{id}")
    public ResponseEntity<TicketsEntity> getTicket(@PathVariable("id") Long id) 
                                                    throws RecordNotFoundException{
        TicketsEntity ticket = service.getTicketById(id);
 
        return new ResponseEntity<TicketsEntity>(ticket, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTicketById(@PathVariable("id") Long id)
                                                    throws RecordNotFoundException {
        service.deleteTicketById(id);
        return HttpStatus.OK;
    }
}