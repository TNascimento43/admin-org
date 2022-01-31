package org.com.iurdapp.service;

import lombok.RequiredArgsConstructor;
import org.com.iurdapp.domain.Group;
import org.com.iurdapp.domain.Group_;
import org.com.iurdapp.domain.Member;
import org.com.iurdapp.domain.Member_;
import org.com.iurdapp.dto.GroupDTO;
import org.com.iurdapp.dto.ResponseDTO;
import org.com.iurdapp.mapper.GroupMapper;
import org.com.iurdapp.repository.GroupRepository;
import org.com.iurdapp.utils.BaseSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService implements BaseSpecs<Group> {

    private final MemberService memberService;
    private final GroupRepository repository;
    private final GroupMapper mapper;

    @Transactional
    public ResponseDTO changeStatus(Long id) {
        Member member = memberService.findById(id);
        if (member == null) {
            return ResponseDTO.builder().status("400").message("Membro não encontrado!").build();
        }
        Group entity = findById(id);
        if (entity == null) {
            return ResponseDTO.builder().status("400").message("Grupo não encontrado!").build();
        }
        entity.setIsActive(entity.getIsActive() ? Boolean.FALSE : Boolean.TRUE);
        return successMessage();
    }

    public Page<GroupDTO> filter(GroupDTO group, Pageable pageable) {
        Specification<Group> spec = buildSpecAnd(porLike(Group_.name, group.getName()), porEqualsJoin(Group_.member, group.getMemberId(), Member_.id));
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    public List<GroupDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public GroupDTO getById(Long id) {
        return mapper.toDto(findById(id));
    }

    @Transactional
    public ResponseDTO save(GroupDTO group) {
        Member member = memberService.findById(group.getMemberId());
        if (member == null) {
            return ResponseDTO.builder().status("400").message("Membro não encontrado!").build();
        }
        Group entity = mapper.toEntity(group);
        entity.setMember(member);
        repository.save(entity);
        return successMessage();
    }

    private Group findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    private ResponseDTO successMessage() {
        return ResponseDTO.builder().status("200").message("Grupo salvo com sucesso").build();
    }
}
