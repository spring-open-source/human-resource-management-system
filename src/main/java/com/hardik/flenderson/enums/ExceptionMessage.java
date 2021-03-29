package com.hardik.flenderson.enums;

import lombok.Getter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {

	ACCESS_TOKEN_EXPIRED(
			"ACCESS TOKEN INCLUDED IN THE HEADER HAS EXPIRED AND CAN NO LONGER BE USED FOR AUTHENTICATION"),
	AUTHENTICATION_BEARER_STRATEGY_NOT_USED(
			"ACCESS TOKEN IS TO BE INCLUDED IN THE HEADER USING THE THE AUTHENTICATION BEARER MECHANISM I.E \"Authorization\" SHOULD BE THE KEY NAME AND VALUE SHOULD BE OF THE FORMAT \"Bearer <Access-Token>\""),
	CORRECT_SCOPE_NOT_SPECIFIED("ACCESS TOKEN INCLUDED DID NOT HAVE A SCOPE OF \"Manager\" OR \"Employee\""),
	INVALID_COMPANY_CODE_AND_NAME("COMBINATION OF COMPANY NAME AND COMPANY CODE DOES NOT EXIST IN THE DATABASE"),
	INVALID_EMPLOYEE_ID("NO EMPLOYEE EXISTS IN THE DATABASE WITH THE PROVIDED ID"),
	INVALID_EMPLOYEE_ISSUE_ID("NO EMPLOYEE ISSUE EXISTS IN THE DATABASE WITH THE PROVIDED ID"),
	INVALID_EMPLOYEE_ROLE_ID("NO EMPLOYEE ROLE EXISTS IN THE DATABASE WITH THE PROVIDED ID"),
	INVALID_EMPLOYEE_EMAIL("NO EMPLOYEE EXISTS IN THE DATABASE WITH THE PROVIDED EMAIL"),
	INVALID_MANAGER_EMAIL("NO MANAGER EXISTS IN THE DATABASE WITH THE PROVIDED EMAIL"),
	INVALID_MANAGER_ID("NO MANAGER EXISTS IN THE DATABASE WITH THE PROVIDED ID"),
	INVALID_MASTER_ROLE_ID("NO MASTER ROLE EXISTS IN THE DATABASE WITH THE PROVIDED ID"),
	INVALID_MONTHLY_SALARY_DETAIL_ID("NO MONTHLY-SALARY-DETAIL RECORD EXISTS IN THE DATABASE WITH THE PROVIDED ID"),
	INVALID_SUPPORT_TICKET_ID("NO SUPPORT TICKET EXISTS IN THE DATABASE WITH THE PROVIDED ID"),
	KEYCLOAK_CODE_EXCHANGE_FAILURE(
			"FAILED TO FETCH ID-TOKEN, REFRESH-TOKEN AND RELEVANT INFORMATION FROM KEYCLOAK SERVER FOR THE CODE PROVIDED"),
	MISSING_ACCESS_TOKEN_AND_USER_ID(
			"CALL TO A PROTECTED API WAS MADE WITHOUT SPECIFYING THE ACCESS TOKEN AND USER-ID IN THE HEADER"),
	REFRESH_ACCESS_TOKEN_FAILURE(
			"FAILED TO FETCH A NEW ID-TOKEN AND RELEVANT INFORMATION FROM KEYCLOAK SERVER FOR THE CODE PROVIDED"),
	USER_LOGOUT_FAILURE(""),
	EMPTY_EMPLOYEE_PROFILE_PICTURE(
			"PROFILE PICTURE IS TO BE PROVIDED AS A VALID MULTIPART-FILE WHEN CALLING THIS ENDPOINT"),
	EMPTY_MANAGER_PROFILE_PICTURE(
			"PROFILE PICTURE IS TO BE PROVIDED AS A VALID MULTIPART-FILE WHEN CALLING THIS ENDPOINT"),
	REJECTED_EMPLOYEE_BEING_DESPERATE("EMPLOYEE HAS BEEN REJECTED/REMOVED FROM THE COMPANY THAT HE IS RE-APPLYING TO"),
	EMPLOYEE_SOCIAL_WITH_NAME_ALREADY_EXISTS(
			"EMPLOYEE HAS A SOCIAL REGISTERED IN THE DATABASE WITH THE NAME --social-name--"),
	INVALID_EMPLOYEE_SOCIAL_ID("NO EMPLOYEE SOCIAL RECORD EXISTS IN THE DATABASE WITH THE PROVIDED ID"),
	INVALID_MANAGER_SOCIAL_ID("NO MANAGER SOCIAL RECORD EXISTS IN THE DATABASE WITH THE PROVIDED ID"),
	MANAGER_SOCIAL_WITH_NAME_ALREADY_EXISTS(
			"MANAGER HAS A SOCIAL REGISTERED IN THE DATABASE WITH THE NAME --social-name--"),
	COMPANY_JOIN_INVITATION_ALREADY_SENT("COMPANY JOINING INVITATION HAS ALREADY BEEN SENT TO THE EMAIL --email--"), 
	INVALID_COMPANY_ID_EXCEPTION("NO COMPANY EXISTS IN THE DATABASE WITH THE PROVIDED ID");

	private final String message;

}
