package com.prestamosasuservicio.backend.service;

import com.prestamosasuservicio.backend.entity.Client;
import com.prestamosasuservicio.backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client save(Client client) {
        // Lógica de Aval Opcional
        if (client.getAval() != null && client.getAval().getId() != null) {
            Client avalExistente = clientRepository.findById(client.getAval().getId())
                    .orElseThrow(() -> new RuntimeException("El aval especificado no existe"));
            client.setAval(avalExistente);
        } else {
            client.setAval(null); // Asegura que sea opcional
        }
        return clientRepository.save(client);
    }

    public List<Client> findAllActive() {
        return clientRepository.findByActiveTrue();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .filter(Client::getActive)
                .orElse(null);
    }

    public Client update(Long id, Client details) {
        Client client = findById(id);
        if (client != null) {
            client.setName(details.getName());
            client.setAddress(details.getAddress());
            client.setPhone(details.getPhone());

            // Actualizar aval si es necesario
            if (details.getAval() != null && details.getAval().getId() != null) {
                clientRepository.findById(details.getAval().getId()).ifPresent(client::setAval);
            }

            return clientRepository.save(client);
        }
        return null;
    }

    public boolean delete(Long id) {
        Client client = findById(id);
        if (client != null) {
            client.setActive(false);
            clientRepository.save(client);
            return true;
        }
        return false;
    }
}