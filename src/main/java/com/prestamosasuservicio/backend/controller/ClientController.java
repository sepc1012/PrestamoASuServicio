package com.prestamosasuservicio.backend.controller;

import com.prestamosasuservicio.backend.entity.Client;
import com.prestamosasuservicio.backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public Client create(@RequestBody Client client) {
        return clientService.save(client);
    }

    @GetMapping
    public List<Client> getAll() {
        return clientService.findAllActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        Client client = clientService.findById(id);
        return client != null ? ResponseEntity.ok(client) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
        Client updated = clientService.update(id, client);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return clientService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}