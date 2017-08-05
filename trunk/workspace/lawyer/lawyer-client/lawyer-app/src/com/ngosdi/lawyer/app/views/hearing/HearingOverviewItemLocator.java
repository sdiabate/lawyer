package com.ngosdi.lawyer.app.views.hearing;

import com.ngosdi.lawyer.app.views.PartView;
import com.ngosdi.lawyer.app.views.common.datalist.DataListItemLocator;
import com.ngosdi.lawyer.beans.Hearing;

public class HearingOverviewItemLocator extends DataListItemLocator<Hearing> {

	@Override
	protected Class<? extends PartView<Hearing>> getPartViewClass() {
		return HearingView.class;
	}

	@Override
	protected String getToolItemId() {
		return "lawyer-app.toolbar.main.item.hearings";
	}
}
