package com.dynatrace.clients.controller;

import com.dynatrace.clients.model.Version;
import com.dynatrace.clients.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/version")
public class VersionController {
    @Autowired
    private ClientRepository clientRepository;
    @Value("${service.version}")
    private String svcVer;
    @Value("${service.date}")
    private String svcDate;

    @GetMapping("")
    public Version getVersion() {
        return new Version("clients", svcVer, svcDate, "OK", "Count: " + clientRepository.count());
    }
}
