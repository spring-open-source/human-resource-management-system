package com.hardik.flenderson.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTemplate {

	MANAGER_ACCOUNT_CREATION("account-creation-success-manager", "Account Created Successfully!"),
	EMPLOYEE_ACCOUNT_CREATION("account-creation-success-employee", "Account Created Successfully!"),
	COMPANY_CREATION("company-creation-success", "Company Created Successfully!"),
	COMPANY_JOINING_REQUEST_SENT("company-joining-request-sent", "Joining Request Sent Successfully!"),
	COMPANY_JOINING_REQUEST_ACCEPTED("company-joining-request-accepted", "Joining Request Approved!"),
	COMPANY_JOINING_REQUEST_REJECTED("company-joining-request-rejected", "Joining Request Rejected!"),
	COMPANY_JOINING_REQUEST_RECIEVED("company-joining-request-recieved", "Company Joining Request Recieved!"),
	REMOVED_FROM_COMPANY("removed-from-company", "Removed From Company!"),
	COMPANY_JOINING_INVITATION("company-joining-invitation", "New Invitation Recieved!"),
	NEW_EVENT_CREATION("new-company-event", "New Event Created!"),
	MONTHLY_ATTENDANCE_REPORT("monthly-attendance-report", "Monthly Attendance Report Is Here!"),
	NEW_ISSUE_RECIEVED("new-issue-recieved", "New Issue Recieved!"),
	ISSUE_RAISED_CONFIRMATION("issue-raised-confirmation", "Issue Raised Successfully!"),
	ISSUE_RESPONSE_RECIEVED("issue-response-recieved", "Issue Response Recieved!"), 
	MONTHLY_PAYROLL_REPORT("monthly-payroll-report", "Monthly Payroll Report Is Ready"), 
	CONTACT_US_CONFIRMATION("contact-us-confirmation", "Thanks For Contacting Us!");

	private final String name;

	private final String subject;

}