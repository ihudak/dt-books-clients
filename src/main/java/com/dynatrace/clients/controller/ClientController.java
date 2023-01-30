package com.dynatrace.clients.controller;

import com.dynatrace.clients.exception.BadRequestException;
import com.dynatrace.clients.exception.ResourceNotFoundException;
import com.dynatrace.clients.model.Client;
import com.dynatrace.clients.repository.ClientRepository;
import com.dynatrace.clients.repository.ConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.sqrt;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController extends HardworkingController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    ConfigRepository configRepository;
    Logger logger = LoggerFactory.getLogger(ClientController.class);

    // get all clients
    @GetMapping("")
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // get a client by id
    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new ResourceNotFoundException("Client not found");
        }
        return client.get();
    }

    // find a client by email
    @GetMapping("/find")
    public Client getClientByEmail(@RequestParam String email) {
        simulateCrash();
        Client clientDb = clientRepository.findByEmail(email);
        if (clientDb == null) {
            throw new ResourceNotFoundException("Client does not exist. Email: " + email);
        }
        return clientDb;
    }

    // create a new client
    @PostMapping("")
    public Client createClient(@RequestBody Client client) {
        simulateHardWork();
        simulateCrash();
        logger.debug("Creating Client " + client.getEmail());
        return clientRepository.save(client);
    }

    // update client
    @PutMapping("/{id}")
    public Client updateClientById(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> clientDb = clientRepository.findById(id);
        if (clientDb.isEmpty()) {
            throw new ResourceNotFoundException("Client not found");
        } else if (client.getId() != id || clientDb.get().getId() != id) {
            throw new BadRequestException("bad client id");
        }
        return clientRepository.save(client);
    }


    // delete a client by id
    @DeleteMapping("/{id}")
    public void deleteClientById(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }

    // delete all clients
    @DeleteMapping("/delete-all")
    public void deleteAllClients() {
        clientRepository.deleteAll();
    }

    @Override
    public ConfigRepository getConfigRepository() {
        return configRepository;
    }
}
