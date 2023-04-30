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


//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController extends HardworkingController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ConfigRepository configRepository;
    private Logger logger = LoggerFactory.getLogger(ClientController.class);

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
            ResourceNotFoundException ex = new ResourceNotFoundException("Client not found");
            logger.error(ex.getMessage());
            throw ex;
        }
        return client.get();
    }

    // find a client by email
    @GetMapping("/find")
    public Client getClientByEmail(@RequestParam String email) {
        simulateHardWork();
        simulateCrash();
        logger.info("Looking for client " + email);
        Client clientDb = clientRepository.findByEmail(email);
        if (clientDb == null) {
            ResourceNotFoundException ex = new ResourceNotFoundException("Client does not exist. Email: " + email);
            logger.error(ex.getMessage());
            throw ex;
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
            ResourceNotFoundException ex = new ResourceNotFoundException("Client not found");
            logger.error(ex.getMessage());
            throw ex;
        } else if (client.getId() != id || clientDb.get().getId() != id) {
            BadRequestException ex = new BadRequestException("bad client id");
            logger.error(ex.getMessage());
            throw ex;
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
        clientRepository.truncateTable();
    }

    @Override
    public ConfigRepository getConfigRepository() {
        return configRepository;
    }
}
