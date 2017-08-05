package com.ngosdi.lawyer.app.views.hearing;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.ngosdi.lawyer.app.views.PartView;
import com.ngosdi.lawyer.beans.Hearing;

public class HearingView extends PartView<Hearing> {

	@Override
	protected void createGui(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		dataListModel = new HearingDataListModel(parent);
	}
}