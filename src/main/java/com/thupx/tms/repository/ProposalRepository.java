package com.thupx.tms.repository;

import com.thupx.tms.domain.Proposal;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Proposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
	List<Proposal> findByStatus(Boolean status);
	
	@Query(value = "SELECT * FROM PROPOSAL WHERE STATUS_CHART = :statusChart and COALESCE(END_DATE,START_DATE) >= :startDate AND COALESCE(END_DATE,START_DATE) <= :endDate", nativeQuery = true) 	
	List<Proposal> findByStatusDateBetween(@Param("statusChart") Boolean statusChart, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);
	 
	
	@Query(value = "SELECT * FROM proposal as p, jhi_user as u, hospital_department as h WHERE p.user_extra_user_id = u.id and p.hospital_department_id = h.id and"
			+ " (UPPER(p.content_proposal) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.current_progress_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.note) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " (CASE WHEN isnumeric(TRIM(:search)) = true THEN p.id = CAST(TRIM(:search) AS INT) END) or"
			+ " UPPER(u.first_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(u.last_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(h.hospital_department_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')))", nativeQuery = true)
	Page<Proposal> findAll(Pageable pageable,@Param("search") String search);
	
	@Query(value = "SELECT * FROM proposal as p, jhi_user as u, hospital_department as h WHERE p.user_extra_user_id = u.id and p.hospital_department_id = h.id and p.user_extra_user_id=:user_extra_user_id and"
			+ " (UPPER(p.content_proposal) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.current_progress_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.note) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " (CASE WHEN isnumeric(TRIM(:search)) = true THEN p.id = CAST(TRIM(:search) AS INT) END) or"
			+ " UPPER(u.first_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(u.last_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(h.hospital_department_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')))", nativeQuery = true)
	Page<Proposal> findByUserExtraUserId(Pageable pageable,@Param("user_extra_user_id") Long user_extra_user_id, @Param("search") String search);
	
}
