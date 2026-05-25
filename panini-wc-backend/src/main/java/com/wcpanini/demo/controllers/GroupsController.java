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
@CrossOrigin("*")
public class GroupsController {

    private final GroupsConsumerService service;

    // CLEAN RESPONSE

    @GetMapping
    public List<StatsDTO> getGroups(
            @RequestParam String email
    ) {

        return service.getGroups(email);
    }

    // RAW RESPONSE

    @GetMapping("/raw")
    public Map<String, Integer> raw(
            @RequestParam String email
    ) {

        return service.getRaw(email);
    }
}