package com.thupx.tms.repository;

import com.thupx.tms.domain.ProgessDetaill;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProgessDetaill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgessDetaillRepository extends JpaRepository<ProgessDetaill, Long> {
	
	List<ProgessDetaill> findAllByProposalIdOrderByIdAsc(Long id);
	
	@Modifying
	@Query("update ProgessDetaill u set u.endDate = ?1 where u.id = ?2")
	int setDoneProgress(ZonedDateTime endDate, Long id);
}
