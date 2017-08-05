package com.ngosdi.lawyer.app.views.legalcase;

import java.util.ArrayList;
import java.util.List;

import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Hearing;

public class CaseDetail {

	private final Case caze;
	private final List<Hearing> comingHearings = new ArrayList<>();
	private final List<Hearing> closedHearings = new ArrayList<>();

	public CaseDetail(final Case caze) {
		this.caze = caze;
	}

	public final Case getCaze() {
		return caze;
	}

	public final List<Hearing> getComingHearings() {
		return comingHearings;
	}

	public final List<Hearing> getClosedHearings() {
		return closedHearings;
	}

}
