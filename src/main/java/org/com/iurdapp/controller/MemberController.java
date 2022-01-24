package org.com.iurdapp.controller;

import lombok.RequiredArgsConstructor;
import org.com.iurdapp.domain.Member;
import org.com.iurdapp.dto.ResponseDTO;
import org.com.iurdapp.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
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
}
