package org.com.iurdapp.controller;

import lombok.RequiredArgsConstructor;
import org.com.iurdapp.dto.GroupDTO;
import org.com.iurdapp.dto.ResponseDTO;
import org.com.iurdapp.service.GroupService;
import org.com.iurdapp.utils.ConstantUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public List<GroupDTO> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody GroupDTO group) {
        ResponseDTO response = service.save(group);
        if (ConstantUtils.BAD_REQUEST.equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filter")
    public Page<GroupDTO> filter(@RequestBody GroupDTO group, Pageable pageable) {
        return service.filter(group, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getById(@PathVariable("id") Long id) {
        GroupDTO group = service.getById(id);
        if (group == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(group);
    }

    @PutMapping("/{id}/chang-status")
    public ResponseEntity<ResponseDTO> changeStatus(@PathVariable("id") Long id) {
        ResponseDTO response = service.changeStatus(id);
        if (ConstantUtils.BAD_REQUEST.equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
