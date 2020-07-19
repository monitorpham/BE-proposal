package com.thupx.tms.service.mapper;


import com.thupx.tms.domain.*;
import com.thupx.tms.service.dto.ProposalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proposal} and its DTO {@link ProposalDTO}.
 */
@Mapper(componentModel = "spring", uses = {HospitalDepartmentMapper.class, UserExtraMapper.class})
public interface ProposalMapper extends EntityMapper<ProposalDTO, Proposal> {

    @Mapping(source = "hospitalDepartment.id", target = "hospitalDepartmentId")
    @Mapping(source = "userExtra.id", target = "userExtraId")
    ProposalDTO toDto(Proposal proposal);

    @Mapping(source = "hospitalDepartmentId", target = "hospitalDepartment")
    @Mapping(source = "userExtraId", target = "userExtra")
    Proposal toEntity(ProposalDTO proposalDTO);

    default Proposal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proposal proposal = new Proposal();
        proposal.setId(id);
        return proposal;
    }
}
