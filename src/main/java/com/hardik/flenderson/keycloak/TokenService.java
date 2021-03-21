package com.hardik.flenderson.keycloak;

import java.util.UUID;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hardik.flenderson.dto.UserLoginSuccessDto;
import com.hardik.flenderson.enums.AccountType;
import com.hardik.flenderson.keycloak.configuration.KeycloakConfiguration;
import com.hardik.flenderson.keycloak.dto.KeycloakTokenDto;
import com.hardik.flenderson.keycloak.request.LogoutUserRequest;
import com.hardik.flenderson.service.EmployeeService;
import com.hardik.flenderson.service.ManagerService;
import com.hardik.flenderson.utility.JwtUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(KeycloakConfiguration.class)
public class TokenService {

	private final KeycloakConfiguration keycloakConfiguration;

	private final RestTemplate restTemplate;

	private final ManagerService managerService;

	private final EmployeeService employeeService;

	public UserLoginSuccessDto getAccessToken(String code) {
		final var configuration = keycloakConfiguration.getKeyCloak();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", configuration.getClientId());
		map.add("grant_type", configuration.getGrantType());
		map.add("code", code);
		map.add("redirect_uri", configuration.getRedirectUri());
		map.add("client_secret", configuration.getClientSecret());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		KeycloakTokenDto keycloakTokenDto = null;
		try {
			keycloakTokenDto = restTemplate.postForObject(configuration.getDomain() + "/auth/realms/"
					+ configuration.getRealmName() + "/protocol/openid-connect/token", request, KeycloakTokenDto.class);
		} catch (Exception exception) {
			throw new RuntimeException();
		}
		final var accountType = keycloakTokenDto.getScope().contains("manager") ? AccountType.MANAGER.getAccountType()
				: AccountType.EMPLOYEE.getAccountType();
		final var keyCloakUser = JwtUtility.getUser(keycloakTokenDto.getAccess_token());
		UUID userId = null;
		if (accountType.equals(AccountType.MANAGER.getAccountType())) {
			userId = managerService.getManager(keyCloakUser).getId();
		} else if (accountType.equals(AccountType.EMPLOYEE.getAccountType())) {
			userId = employeeService.getEmployee(keyCloakUser).getId();
		} else {
			throw new RuntimeException("NO ACC TYPE");
		}
		return UserLoginSuccessDto.builder().idToken(keycloakTokenDto.getAccess_token())
				.refreshToken(keycloakTokenDto.getRefresh_token()).userId(userId)
				.accountType(accountType).build();
	}

	public KeycloakTokenDto refreshToken(String refreshToken) {
		final var configuration = keycloakConfiguration.getKeyCloak();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", configuration.getClientId());
		map.add("grant_type", "refresh_token");
		map.add("refresh_token", refreshToken);
		map.add("client_secret", configuration.getClientSecret());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		KeycloakTokenDto keycloakTokenDto = null;
		try {
			keycloakTokenDto = restTemplate.postForObject(configuration.getDomain() + "/auth/realms/"
					+ configuration.getRealmName() + "/protocol/openid-connect/token", request, KeycloakTokenDto.class);
		} catch (Exception exception) {
			throw new RuntimeException();
		}
		return keycloakTokenDto;
	}

	public String getKeyCloakUrl(String accountType) {
		final var configuration = keycloakConfiguration.getKeyCloak();
		return configuration.getDomain() + "/auth/realms/" + configuration.getRealmName()
				+ "/protocol/openid-connect/auth?client_id=" + configuration.getClientId() + "&redirect_uri="
				+ configuration.getRedirectUri() + "&response_type=code&scope=" + accountType;
	}

	public void logout(LogoutUserRequest logoutUserRequest) {
		final var configuration = keycloakConfiguration.getKeyCloak();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("Authorization", "Bearer "+logoutUserRequest.getAccessToken());
		map.add("refresh_token", logoutUserRequest.getRefreshToken());
		map.add("client_id", configuration.getClientId());
		map.add("client_secret", configuration.getClientSecret());
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		
		restTemplate.postForLocation(configuration.getDomain() + "/auth/realms/"
				+ configuration.getRealmName() + "/protocol/openid-connect/logout", request);
		
	}

}
