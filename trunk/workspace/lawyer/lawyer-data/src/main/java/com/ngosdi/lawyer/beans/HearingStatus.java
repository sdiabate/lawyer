package com.ngosdi.lawyer.beans;

public enum HearingStatus {
	CONSTITUTION("Déclaration de constritution"), 
	INSTANCE("En instance"), 
	DEPOT_REPLIQUE("Dépot de replique"), 
	EXAMEN("En examen"), 
	PLEDOIRIE("A plaider"), 
	POUR_REPLIQUE("Pour r�plique"), 
	EN_ETAT("En l'état"), 
	CONCILIATION("Pour la conciliation");

	private String name;

	private HearingStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
