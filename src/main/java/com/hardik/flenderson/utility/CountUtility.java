package com.hardik.flenderson.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CountUtility {
	
	private Integer count;
	
	public static CountUtility createCount() {
		return new CountUtility(2);
	}
	
	public void increment() {
		this.count++;
	}
	
	public void refresh() {
		this.count = 2;
	}
	
}
