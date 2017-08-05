package com.ngosdi.lawyer.beans;

public enum PaymentType {
	CASH("Cache"),
	CHEQ("Chèque"),
	VIR("Virement")
	;

	private String name;

	private PaymentType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
