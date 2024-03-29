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
	
	@Query(value = "SELECT * FROM PROPOSAL order by id ", nativeQuery = true) 	
	List<Proposal> findAllOrderById();
	
	@Query(value = "SELECT * FROM PROPOSAL where user_extra_user_id =:user_extra_user_id order by id ", nativeQuery = true) 	
	List<Proposal> findAllByIdOrderById(@Param("user_extra_user_id") Long user_extra_user_id);
	
	@Query(value = "SELECT * FROM PROPOSAL as p, jhi_user as u, hospital_department as h WHERE p.user_extra_user_id = u.id and p.hospital_department_id = h.id and"
			+ " (UPPER(p.content_proposal) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.current_progress_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.note) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " (CASE WHEN TRIM(:search) ~ '^[0-9]+$' THEN p.id = CAST(TRIM(:search) AS INT) END) or"
			+ " UPPER(u.first_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(u.last_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(h.hospital_department_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')))", nativeQuery = true)
	Page<Proposal> findAll(Pageable pageable,@Param("search") String search);
	
	@Query(value = "SELECT * FROM proposal as p, jhi_user as u, hospital_department as h WHERE p.user_extra_user_id = u.id and p.hospital_department_id = h.id and p.user_extra_user_id=:user_extra_user_id and"
			+ " (UPPER(p.content_proposal) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.current_progress_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.note) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " (CASE WHEN TRIM(:search) ~ '^[0-9]+$' THEN p.id = CAST(TRIM(:search) AS INT) END) or"
			+ " UPPER(u.first_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(u.last_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(h.hospital_department_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')))", nativeQuery = true)
	Page<Proposal> findByUserExtraUserId(Pageable pageable,@Param("user_extra_user_id") Long user_extra_user_id, @Param("search") String search);
	
	@Query(value = "SELECT * FROM proposal as p, jhi_user as u, hospital_department as h, user_extra as ue WHERE p.user_extra_user_id = u.id and p.hospital_department_id = h.id and u.id = ue.user_id and ue.equiqment_group_id=:equiqment_group_id and"
			+ " (UPPER(p.content_proposal) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.current_progress_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.note) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " (CASE WHEN TRIM(:search) ~ '^[0-9]+$' THEN p.id = CAST(TRIM(:search) AS INT) END) or"
			+ " UPPER(u.first_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(u.last_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(h.hospital_department_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')))", nativeQuery = true)
	Page<Proposal> findByUserExtraEquiqmentId(Pageable pageable,@Param("equiqment_group_id") Long equiqment_group_id, @Param("search") String search);
	
	@Query(value = "SELECT * FROM proposal as p, jhi_user as u, hospital_department as h WHERE p.user_extra_user_id = u.id and p.hospital_department_id = h.id and p.status=:status and"
			+ " (UPPER(p.content_proposal) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.current_progress_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.note) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " (CASE WHEN  TRIM(:search) ~ '^[0-9]+$' THEN p.id = CAST(TRIM(:search) AS INT) END) or"
			+ " UPPER(u.first_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(u.last_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(h.hospital_department_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')))", nativeQuery = true)
	Page<Proposal> findStatus(Pageable pageable, @Param("search") String search,@Param("status") Boolean status);
	
	@Query(value = "SELECT * FROM proposal as p, jhi_user as u, hospital_department as h WHERE p.user_extra_user_id = u.id and p.hospital_department_id = h.id and p.user_extra_user_id=:user_extra_user_id and p.status=:status and"
			+ " (UPPER(p.content_proposal) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.current_progress_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.note) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " (CASE WHEN TRIM(:search) ~ '^[0-9]+$' THEN p.id = CAST(TRIM(:search) AS INT) END) or"
			+ " UPPER(u.first_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(u.last_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(h.hospital_department_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')))", nativeQuery = true)
	Page<Proposal> findByUserExtraUserIdStatus(Pageable pageable,@Param("user_extra_user_id") Long user_extra_user_id, @Param("search") String search,@Param("status") Boolean status);
	
	@Query(value = "SELECT * FROM proposal as p, jhi_user as u, hospital_department as h, user_extra as ue WHERE p.user_extra_user_id = u.id and p.hospital_department_id = h.id and u.id = ue.user_id and ue.equiqment_group_id=:equiqment_group_id and p.status=:status and"
			+ " (UPPER(p.content_proposal) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.current_progress_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(p.note) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " (CASE WHEN TRIM(:search) ~ '^[0-9]+$' THEN p.id = CAST(TRIM(:search) AS INT) END) or"
			+ " UPPER(u.first_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(u.last_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')) or"
			+ " UPPER(h.hospital_department_name) LIKE UPPER(CONCAT('%', TRIM(:search), '%')))", nativeQuery = true)
	Page<Proposal> findByUserExtraEquiqmentIdStatus(Pageable pageable,@Param("equiqment_group_id") Long equiqment_group_id, @Param("search") String search,@Param("status") Boolean status);
}
