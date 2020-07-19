package com.thupx.tms.repository;

import com.thupx.tms.domain.UserExtra;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserExtra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long> {
	
	List<UserExtra> findAllByEquiqmentGroupId(Long id);
}
