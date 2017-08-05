package com.ngosdi.lawyer.app.views.client;

import com.ngosdi.lawyer.app.views.PartView;
import com.ngosdi.lawyer.app.views.common.datalist.DataListItemLocator;
import com.ngosdi.lawyer.beans.Client;

public class AdversaryOverviewItemLocator extends DataListItemLocator<Client> {

	@Override
	protected Class<? extends PartView<Client>> getPartViewClass() {
		return AdversaryView.class;
	}

	@Override
	protected String getToolItemId() {
		return "lawyer-app.toolbar.main.item.adversary";
	}
}
