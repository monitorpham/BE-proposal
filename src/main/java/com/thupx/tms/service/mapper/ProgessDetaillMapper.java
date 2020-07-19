package com.thupx.tms.service.mapper;


import com.thupx.tms.domain.*;
import com.thupx.tms.service.dto.ProgessDetaillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProgessDetaill} and its DTO {@link ProgessDetaillDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProposalMapper.class, ProgressMapper.class})
public interface ProgessDetaillMapper extends EntityMapper<ProgessDetaillDTO, ProgessDetaill> {

    @Mapping(source = "proposal.id", target = "proposalId")
    @Mapping(source = "progress.id", target = "progressId")
    ProgessDetaillDTO toDto(ProgessDetaill progessDetaill);

    @Mapping(source = "proposalId", target = "proposal")
    @Mapping(source = "progressId", target = "progress")
    ProgessDetaill toEntity(ProgessDetaillDTO progessDetaillDTO);

    default ProgessDetaill fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProgessDetaill progessDetaill = new ProgessDetaill();
        progessDetaill.setId(id);
        return progessDetaill;
    }
}
