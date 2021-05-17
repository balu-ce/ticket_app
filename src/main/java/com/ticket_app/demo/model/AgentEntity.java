package com.ticket_app.demo.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.ticket_app.demo.model.TicketsEntity;

@Entity
@Table(name="AGENT")
public class AgentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="email")
    private String email;

	@Column(name="count")
    private int count;

	@JsonBackReference(value="created_agent")
	@OneToMany(mappedBy="created_agent", cascade = CascadeType.ALL)
    private Set<TicketsEntity> created_agent;

	@JsonBackReference(value="assigned_agent")
	@OneToMany(mappedBy="assigned_agent", cascade = CascadeType.ALL)
    private Set<TicketsEntity> assigned_agent;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Set<TicketsEntity> getCreated_agent() {
        return created_agent;
    }

    public void setCreated_agent(Set<TicketsEntity> created_agent) {
        this.created_agent = created_agent;
    }

	public Set<TicketsEntity> getAssigned_agent() {
        return assigned_agent;
    }

    public void setAssigned_agent(Set<TicketsEntity> assigned_agent) {
        this.assigned_agent = assigned_agent;
    }

    @Override
    public String toString() {
        return "AgentEntity [id=" + id + ", name=" + name + 
                            ", email=" + email   + "]";
    }
}