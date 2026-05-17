package com.wcpanini.demo.controllers;

import com.wcpanini.demo.dtos.StatsDTO;
import com.wcpanini.demo.services.GroupsConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupsController {

    private final GroupsConsumerService service;

    // CLEAN RESPONSE

    @GetMapping
    public List<StatsDTO> getGroups() {

        return service.getGroups();
    }

    // RAW RESPONSE

    @GetMapping("/raw")
    public Map<String, Integer> raw() {

        return service.getRaw();
    }
}