package com.prestamosasuservicio.backend.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;
    private String phone;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aval_id")
    @JsonIgnoreProperties({"aval", "active"})
    private Client aval;

    public Client(){}

    public Client(Long id, String name, String address, String phone, Client aval) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.aval = aval;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Client getAval() { return aval; }
    public void setAval(Client aval) { this.aval = aval; }
}