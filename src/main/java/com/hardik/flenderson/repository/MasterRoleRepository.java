package com.hardik.flenderson.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.MasterRole;

@Repository
public interface MasterRoleRepository extends JpaRepository<MasterRole, UUID>{

}
