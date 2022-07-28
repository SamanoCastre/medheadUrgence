package com.urgence.reservation.utils;

public enum TypeAction {
	ANNULER("annuler"),
	TERMINER("terminer");
	private String action;

	TypeAction(String action) {
		this.action = action;
	}
	
	public String get() {
		return this.action;
	}
}
