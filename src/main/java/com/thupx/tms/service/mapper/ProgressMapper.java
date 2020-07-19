package com.thupx.tms.service.mapper;


import com.thupx.tms.domain.*;
import com.thupx.tms.service.dto.ProgressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Progress} and its DTO {@link ProgressDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProgressMapper extends EntityMapper<ProgressDTO, Progress> {



    default Progress fromId(Long id) {
        if (id == null) {
            return null;
        }
        Progress progress = new Progress();
        progress.setId(id);
        return progress;
    }
}
