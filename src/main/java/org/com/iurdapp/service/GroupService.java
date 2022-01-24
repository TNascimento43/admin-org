package org.com.iurdapp.service;

import lombok.RequiredArgsConstructor;
import org.com.iurdapp.domain.Group;
import org.com.iurdapp.dto.ResponseDTO;
import org.com.iurdapp.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository repository;

    public List<Group> getAll() {
        return repository.findAll();
    }

    @Transactional
    public ResponseDTO save(Group group) {
        repository.save(group);
        return ResponseDTO.builder().message("Grupo salvo com sucesso").build();
    }
}
