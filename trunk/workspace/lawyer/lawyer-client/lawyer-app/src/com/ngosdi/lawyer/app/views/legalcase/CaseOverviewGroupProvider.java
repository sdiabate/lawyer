package com.ngosdi.lawyer.app.views.legalcase;

import java.util.List;
import java.util.stream.Collectors;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.views.overview.IOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.overview.IOverviewItemLocator;
import com.ngosdi.lawyer.app.views.overview.OverviewItem;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.services.IServiceDao;

public class CaseOverviewGroupProvider implements IOverviewGroupProvider<Case> {

	@Override
	public String getTitle() {
		return "Affaires recemment enregistrées";
	}

	@Override
	public List<OverviewItem<Case>> getOverviewItems() {
		return getOverviewCases().stream().map(
				caze -> new OverviewItem<Case>(caze, "Affaire n°: " + caze.getCaseId() + " Client: " + caze.getClient().getFirstName() + " " + caze.getClient().getLastName() + " Juridiction: NPE"))
				.collect(Collectors.toList());
	}

	@Override
	public IOverviewItemLocator<Case> getItemLocator() {
		return new CaseOverviewItemLocator();
	}

	protected List<Case> getOverviewCases() {
		return BundleUtil.getService(IServiceDao.class).getLatestElements(Case.class, 10);
	}
}
