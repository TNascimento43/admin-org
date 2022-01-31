package org.com.iurdapp.mapper;

import org.com.iurdapp.domain.Group;
import org.com.iurdapp.dto.GroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "memberName", source = "member.name")
    GroupDTO toDto(Group entity);

    @Mapping(source = "memberId", target = "member.id")
    Group toEntity(GroupDTO group);
}
