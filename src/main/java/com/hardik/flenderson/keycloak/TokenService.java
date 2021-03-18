package com.hardik.flenderson.keycloak;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.hardik.flenderson.keycloak.configuration.KeycloakConfiguration;
import com.hardik.flenderson.keycloak.dto.KeycloakTokenDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(KeycloakConfiguration.class)
public class TokenService {

	private final KeycloakConfiguration keycloakConfiguration;

	private final RestTemplate restTemplate;

	public KeycloakTokenDto getAccessToken(String code) {
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
		return keycloakTokenDto;
	}

	public String getKeyCloakUrl(String accountType) {
		final var configuration = keycloakConfiguration.getKeyCloak();
		return configuration.getDomain() + "/auth/realms/" + configuration.getRealmName()
				+ "/protocol/openid-connect/auth?client_id=" + configuration.getClientId() + "&redirect_uri="
				+ configuration.getRedirectUri() + "&response_type=code&scope=" + accountType;
	}

}
