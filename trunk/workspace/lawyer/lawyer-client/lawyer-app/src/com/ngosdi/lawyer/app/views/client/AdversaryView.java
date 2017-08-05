package com.ngosdi.lawyer.app.views.client;

import com.ngosdi.lawyer.beans.ClientType;

public class AdversaryView extends ClientView {

	@Override
	protected ClientType geClientType() {
		return ClientType.ADVERSARY;
	}
}