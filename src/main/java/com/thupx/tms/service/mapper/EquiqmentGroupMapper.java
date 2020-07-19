package com.thupx.tms.service.mapper;


import com.thupx.tms.domain.*;
import com.thupx.tms.service.dto.EquiqmentGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EquiqmentGroup} and its DTO {@link EquiqmentGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface EquiqmentGroupMapper extends EntityMapper<EquiqmentGroupDTO, EquiqmentGroup> {

    @Mapping(source = "department.id", target = "departmentId")
    EquiqmentGroupDTO toDto(EquiqmentGroup equiqmentGroup);

    @Mapping(source = "departmentId", target = "department")
    EquiqmentGroup toEntity(EquiqmentGroupDTO equiqmentGroupDTO);

    default EquiqmentGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        EquiqmentGroup equiqmentGroup = new EquiqmentGroup();
        equiqmentGroup.setId(id);
        return equiqmentGroup;
    }
}
