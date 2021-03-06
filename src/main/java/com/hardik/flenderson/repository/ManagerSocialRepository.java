package com.hardik.flenderson.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.ManagerSocial;

@Repository
public interface ManagerSocialRepository extends JpaRepository<ManagerSocial, UUID>{
	
	Boolean existsByManagerIdAndName(UUID managerId, String name);

}
