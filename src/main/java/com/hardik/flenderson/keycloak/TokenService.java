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
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.CorrectScopeNotSpecifiedException;
import com.hardik.flenderson.exception.KeycloakCodeExchangeFailureException;
import com.hardik.flenderson.exception.RefreshAccessTokenFailureException;
import com.hardik.flenderson.exception.UserLogoutFailureException;
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
			throw new KeycloakCodeExchangeFailureException(
					ExceptionMessage.KEYCLOAK_CODE_EXCHANGE_FAILURE.getMessage());
		}
		final var accountType = keycloakTokenDto.getScope().contains("manager") ? AccountType.MANAGER.getAccountType()
				: AccountType.EMPLOYEE.getAccountType();
		final var keyCloakUser = JwtUtility.getUser(keycloakTokenDto.getAccess_token());
		UUID userId = null;
		if (accountType.equals(AccountType.MANAGER.getAccountType())) {
			userId = managerService.getManagerFromKeycloakUserHandler(keyCloakUser).getId();
		} else if (accountType.equals(AccountType.EMPLOYEE.getAccountType())) {
			userId = employeeService.getEmployeeFromKeycloakUserHandler(keyCloakUser).getId();
		} else {
			throw new CorrectScopeNotSpecifiedException(ExceptionMessage.CORRECT_SCOPE_NOT_SPECIFIED.getMessage());
		}
		return UserLoginSuccessDto.builder().idToken(keycloakTokenDto.getAccess_token())
				.refreshToken(keycloakTokenDto.getRefresh_token()).userId(userId).accountType(accountType).build();
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
			throw new RefreshAccessTokenFailureException(ExceptionMessage.REFRESH_ACCESS_TOKEN_FAILURE.getMessage());
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
		map.add("Authorization", "Bearer " + logoutUserRequest.getAccessToken());
		map.add("refresh_token", logoutUserRequest.getRefreshToken());
		map.add("client_id", configuration.getClientId());
		map.add("client_secret", configuration.getClientSecret());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
			restTemplate.postForLocation(configuration.getDomain() + "/auth/realms/" + configuration.getRealmName()
					+ "/protocol/openid-connect/logout", request);
		} catch (Exception exception) {
			throw new UserLogoutFailureException(ExceptionMessage.USER_LOGOUT_FAILURE.getMessage());
		}

	}

}
