package com.thupx.tms.service.mapper;


import com.thupx.tms.domain.*;
import com.thupx.tms.service.dto.HospitalDepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link HospitalDepartment} and its DTO {@link HospitalDepartmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HospitalDepartmentMapper extends EntityMapper<HospitalDepartmentDTO, HospitalDepartment> {



    default HospitalDepartment fromId(Long id) {
        if (id == null) {
            return null;
        }
        HospitalDepartment hospitalDepartment = new HospitalDepartment();
        hospitalDepartment.setId(id);
        return hospitalDepartment;
    }
}
