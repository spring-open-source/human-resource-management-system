package com.hardik.flenderson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.MasterCountry;

@Repository
public interface MasterCountryRepository extends JpaRepository<MasterCountry, Integer>{

}
