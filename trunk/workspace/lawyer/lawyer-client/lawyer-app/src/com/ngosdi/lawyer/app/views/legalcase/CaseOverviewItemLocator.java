package com.ngosdi.lawyer.app.views.legalcase;

import com.ngosdi.lawyer.app.views.PartView;
import com.ngosdi.lawyer.app.views.common.datalist.DataListItemLocator;
import com.ngosdi.lawyer.beans.Case;

public class CaseOverviewItemLocator extends DataListItemLocator<Case> {

	@Override
	protected Class<? extends PartView<Case>> getPartViewClass() {
		return CaseView.class;
	}

	@Override
	protected String getToolItemId() {
		return "lawyer-app.toolbar.main.item.cases";
	}
}
