package com.ngosdi.lawyer.app.views.payment;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.ngosdi.lawyer.app.views.PartView;
import com.ngosdi.lawyer.beans.Payment;

public class PaymentView extends PartView<Payment> {

	@Override
	protected void createGui(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		dataListModel = new PaymentDataListModel(parent);
	}
}