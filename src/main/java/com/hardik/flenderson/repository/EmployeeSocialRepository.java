package com.hardik.flenderson.repository;

import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.EmployeeSocial;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmployeeSocialRepository extends JpaRepository<EmployeeSocial, UUID>{

}
