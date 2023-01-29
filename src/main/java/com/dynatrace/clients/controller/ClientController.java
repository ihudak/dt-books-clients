package com.dynatrace.clients.controller;

import com.dynatrace.clients.exception.BadRequestException;
import com.dynatrace.clients.exception.ResourceNotFoundException;
import com.dynatrace.clients.model.Client;
import com.dynatrace.clients.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    // get all clients
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // get a client by id
    @GetMapping("/clients/{id}")
    public Client getClientById(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new ResourceNotFoundException("Client not found");
        }
        return client.get();
    }

    // find a client by email
    @GetMapping("/clients/find")
    public Client getClientByEmail(@RequestParam String email) {
        Client clientDb = clientRepository.findByEmail(email);
        if (clientDb == null) {
            throw new ResourceNotFoundException("Client does not exist. Email: " + email);
        }
        return clientDb;
    }

    // create a new client
    @PostMapping("/clients")
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    // update client
    @PutMapping("/clients/{id}")
    public Client updateClientById(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> clientDB = clientRepository.findById(id);
        if (clientDB.isEmpty()) {
            throw new ResourceNotFoundException("Client not found");
        } else if (client.getId() != id || clientDB.get().getId() != id) {
            throw new BadRequestException("bad client id");
        }
        return clientRepository.save(client);
    }


    // delete a client by id
    @DeleteMapping("clients/{id}")
    public void deleteClientById(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }

    // delete all clients
    @DeleteMapping("/clients/delete-all")
    public void deleteAllClients() {
        clientRepository.deleteAll();
    }
}
