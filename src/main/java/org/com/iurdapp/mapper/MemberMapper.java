package org.com.iurdapp.mapper;

import org.com.iurdapp.domain.Member;
import org.com.iurdapp.dto.MemberDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberDTO toDto(Member byId);
}
