package com.apsi.modules.user.domain;

public enum SystemRole {

	ADMINISTRATOR ("Administrator"),
	EMPLOYEE ("Pracownik"),
	USER ("Pracownik zewnętrzny");

	private final String name;

	SystemRole(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
