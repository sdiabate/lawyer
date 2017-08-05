package com.ngosdi.lawyer.app.views.client;

import java.util.ArrayList;
import java.util.List;

import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.Hearing;

public class ClientDetail {

	private final Client client;
	private final List<Hearing> comingHearings = new ArrayList<>();
	private final List<Hearing> closedHearings = new ArrayList<>();
	private final List<Case> inProgressCases = new ArrayList<>();
	private final List<Case> closedCases = new ArrayList<>();

	public ClientDetail(final Client client) {
		this.client = client;
	}

	public final Client getClient() {
		return client;
	}

	public final List<Hearing> getComingHearings() {
		return comingHearings;
	}

	public List<Hearing> getClosedHearings() {
		return closedHearings;
	}

	public final List<Case> getInProgressCases() {
		return inProgressCases;
	}

	public final List<Case> getClosedCases() {
		return closedCases;
	}

}
