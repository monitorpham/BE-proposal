package com.thupx.tms.repository;

import com.thupx.tms.domain.HospitalDepartment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HospitalDepartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {
}
