package org.com.iurdapp.controller;

import lombok.RequiredArgsConstructor;
import org.com.iurdapp.domain.Member;
import org.com.iurdapp.dto.GroupDTO;
import org.com.iurdapp.dto.MemberDTO;
import org.com.iurdapp.dto.ResponseDTO;
import org.com.iurdapp.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService service;

    @GetMapping
    public List<Member> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseDTO save(@RequestBody Member member) {
        return service.save(member);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getById(@PathVariable("id") Long id) {
        MemberDTO member = service.getById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(member);
    }
}
