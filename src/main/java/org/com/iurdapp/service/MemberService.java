package org.com.iurdapp.service;

import lombok.RequiredArgsConstructor;
import org.com.iurdapp.domain.Member;
import org.com.iurdapp.dto.ResponseDTO;
import org.com.iurdapp.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository repository;

    public List<Member> getAll() {
        return repository.findAll();
    }

    @Transactional
    public ResponseDTO save(Member member) {
        repository.save(member);
        return ResponseDTO.builder().message("Membro salvo com sucesso").build();
    }
}
