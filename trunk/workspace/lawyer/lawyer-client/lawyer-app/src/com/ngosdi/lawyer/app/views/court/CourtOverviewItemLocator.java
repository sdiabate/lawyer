package com.ngosdi.lawyer.app.views.court;

import com.ngosdi.lawyer.app.views.PartView;
import com.ngosdi.lawyer.app.views.common.datalist.DataListItemLocator;
import com.ngosdi.lawyer.beans.Court;

public class CourtOverviewItemLocator extends DataListItemLocator<Court> {

	@Override
	protected Class<? extends PartView<Court>> getPartViewClass() {
		return CourtView.class;
	}

	@Override
	protected String getToolItemId() {
		return "lawyer-app.toolbar.main.item.courts";
	}
}
