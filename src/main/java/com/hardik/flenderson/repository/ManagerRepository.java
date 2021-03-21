package com.hardik.flenderson.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID>{

}
