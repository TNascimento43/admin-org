package org.com.iurdapp.controller;

import lombok.RequiredArgsConstructor;
import org.com.iurdapp.domain.Group;
import org.com.iurdapp.dto.ResponseDTO;
import org.com.iurdapp.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupService service;

    @GetMapping
    public List<Group> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseDTO save(@RequestBody Group group) {
        return service.save(group);
    }
}
