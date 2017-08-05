package com.ngosdi.lawyer.app.views.overview;

import java.util.ArrayList;
import java.util.List;

import com.ngosdi.lawyer.app.views.client.ClientOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.hearing.HearingOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.legalcase.CaseOverviewGroupProvider;

public final class OverviewProvider {

	public static final OverviewProvider INSTANCE = new OverviewProvider();

	private final List<IOverviewGroupProvider<?>> overviewGroupProviders = new ArrayList<IOverviewGroupProvider<?>>();

	private OverviewProvider() {
		overviewGroupProviders.add(new HearingOverviewGroupProvider());
		overviewGroupProviders.add(new CaseOverviewGroupProvider());
		overviewGroupProviders.add(new ClientOverviewGroupProvider());
	}

	public List<IOverviewGroupProvider<?>> getOverviewGroupProviders() {
		return overviewGroupProviders;
	}
}
