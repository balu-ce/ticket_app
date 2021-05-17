package com.ticket_app.demo.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.EnumType;

import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="TICKETS")
public class TicketsEntity {

    public static enum Status {
    OPEN,WAITING_ON_CUSTOMER,CUSTOMER_RESPONDED,RESOLVED,CLOSED;
    }

    public enum Priority {
    HIGH,MEDIUM,LOW
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="type")
    private String type;

    @Column(name="description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;

    @Column(name="created")
    private long created;

    @Column(name="updated")
    private long updated;

    @Enumerated(EnumType.STRING)
    @Column(name="priority")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "created_agent")
    private AgentEntity created_agent;

    @ManyToOne
    @JoinColumn(name = "assigned_agent")
    private AgentEntity assigned_agent;

    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerEntity customer;
    
    public TicketsEntity() {
        Calendar calendar = Calendar.getInstance();
        long t = calendar.getTimeInMillis();
        created = t;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}
    
    public Long getUpdated() {
		return updated;
	}

	public void setUpdated(Long updated) {
		this.updated = updated;
	}

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

    public String getStatus() {
		return status.name();
	}

    public Status getStatusasEnum() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

    public AgentEntity getCreated_agent() {
        return created_agent;
    }

    public void setCreated_agent(AgentEntity created_agent) {
        this.created_agent = created_agent;
    }

    public AgentEntity getAssigned_agent() {
        return assigned_agent;
    }

    public void setAssigned_agent(AgentEntity assigned_agent) {
        this.assigned_agent = assigned_agent;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "TicketsEntity [id=" + id +
                            ", type=" + type + ", description=" +description+ 
                            ", status=" +status+ ", priority=" +priority+ ", created_agent=" 
                            + created_agent + ", assigned_agent=" + assigned_agent+
                            ", customer=" + customer + "]";
    }
}