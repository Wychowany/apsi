package com.apsi.global;

public enum Language {
	PL,
	ENG;

	public static Language resolve(String name) {
		switch (name) {
			case "PL":
				return PL;
			case "ENG":
				return ENG;
			default:
				throw new RuntimeException("Unsupported language");
		}
	}
}
