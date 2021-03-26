package com.hardik.flenderson.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "company_join_invitations")
@Data
public class CompanyJoinInvitation implements Serializable {

	private static final long serialVersionUID = 5334309023878440132L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email_id", nullable = false)
	private String emailId;

	@Column(name = "company_id", nullable = false)
	private UUID companyId;

	@Hidden
	@Exclude
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "company_id", nullable = false, insertable = false, updatable = false)
	private Company company;

	@Column(name = "sent_at", nullable = false)
	private LocalDateTime sentAt;

}
