package com.hardik.flenderson.utility;

import java.util.List;
import java.util.stream.Collectors;

import com.hardik.flenderson.enums.IssueType;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeIssueUtility {

	public String getIssueType(Integer issueId) {
		return List.of(IssueType.values()).stream().filter(issue -> issue.getIssueId().equals(issueId))
				.collect(Collectors.toList()).get(0).getIssue();
	}

}
