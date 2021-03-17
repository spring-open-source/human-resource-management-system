package com.hardik.flenderson.repository;


import com.hardik.flenderson.entity.CompanyEvent;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEventRepository extends JpaRepository<CompanyEvent, UUID>{

}
