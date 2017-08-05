package com.ngosdi.lawyer.app.views.court;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.ngosdi.lawyer.app.views.PartView;
import com.ngosdi.lawyer.beans.Court;

public class CourtView extends PartView<Court> {

	@Override
	protected void createGui(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		dataListModel = new CourtDataListModel(parent);
	}
}