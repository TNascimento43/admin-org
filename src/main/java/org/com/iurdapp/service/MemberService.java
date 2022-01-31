package org.com.iurdapp.service;

import lombok.RequiredArgsConstructor;
import org.com.iurdapp.domain.Member;
import org.com.iurdapp.dto.MemberDTO;
import org.com.iurdapp.dto.ResponseDTO;
import org.com.iurdapp.mapper.MemberMapper;
import org.com.iurdapp.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository repository;
    private final MemberMapper mapper;

    public Member findById(Long memberId) {
        return repository.findById(memberId).orElse(null);
    }

    public List<Member> getAll() {
        return repository.findAll();
    }

    public MemberDTO getById(Long id) {
        return mapper.toDto(findById(id));
    }

    @Transactional
    public ResponseDTO save(Member member) {
        repository.save(member);
        return ResponseDTO.builder().message("Membro salvo com sucesso").build();
    }
}
