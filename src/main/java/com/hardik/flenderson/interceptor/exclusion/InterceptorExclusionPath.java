package com.hardik.flenderson.interceptor.exclusion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InterceptorExclusionPath {

	GET_KEYCLOAK_URL_API("/v1/get-keycloak-url/**"), GET_SUPPORT_TICKET_API("/v1/support-tickets"),
	CREATE_SUPPORT_TICKET_API("/v1/support-ticket"), MODIFY_SUPPORT_TICKET_API("/v1/support-ticket/**"),
	GET_MASTER_ROLES_API("/v1/master-roles"), GET_COUNTRIES_API("/v1/countries"),
	EXCHANGE_TOKEN_API("/v1/get-token/**"), REFRESH_TOKEN_API("/v1/refresh-token/**	"), S3_FILE_API("/v1/file");

	private final String path;

}
