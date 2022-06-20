package com.thupx.tms.repository;

import com.thupx.tms.domain.Proposal;

import java.time.ZonedDateTime;
import java.util.List;
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
	@Query(value = "SELECT * FROM PROPOSAL WHERE STATUS = :status and COALESCE(END_DATE,START_DATE) >= :startDate AND COALESCE(END_DATE,START_DATE) <= :endDate", nativeQuery = true) 
	
	List<Proposal> findByStatusDateBetween(@Param("status") Boolean status, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);
	
}
