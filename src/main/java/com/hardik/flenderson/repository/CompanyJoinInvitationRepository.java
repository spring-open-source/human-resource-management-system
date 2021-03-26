package com.hardik.flenderson.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.CompanyJoinInvitation;

@Repository
public interface CompanyJoinInvitationRepository extends JpaRepository<CompanyJoinInvitation, UUID> {

	Boolean existsByEmailIdAndCompanyId(String emailId, UUID companyId);
}
