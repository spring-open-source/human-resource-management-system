package com.hardik.flenderson.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.CompanyEvent;

@Repository
public interface CompanyEventRepository extends JpaRepository<CompanyEvent, UUID>{

}
