package com.thupx.tms.repository;

import com.thupx.tms.domain.EquiqmentGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EquiqmentGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquiqmentGroupRepository extends JpaRepository<EquiqmentGroup, Long> {
}
