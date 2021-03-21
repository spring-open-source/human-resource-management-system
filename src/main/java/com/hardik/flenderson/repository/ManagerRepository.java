package com.hardik.flenderson.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID>{
	
	Boolean existsByEmailIdIgnoreCase(String email);
	
	Optional<Manager> findByEmailIdIgnoreCase(String emailId);

}
