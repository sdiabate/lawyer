package com.ngosdi.lawyer.app.views.court;

import java.util.List;
import java.util.stream.Collectors;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.views.overview.IOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.overview.IOverviewItemLocator;
import com.ngosdi.lawyer.app.views.overview.OverviewItem;
import com.ngosdi.lawyer.beans.Court;
import com.ngosdi.lawyer.services.IServiceDao;

public class CourtOverviewGroupProvider implements IOverviewGroupProvider<Court> {

	@Override
	public String getTitle() {
		return "Cours recemment enregistrés";
	}

	@Override
	public List<OverviewItem<Court>> getOverviewItems() {
		return getOverviewCourts().stream().map(court -> createOverviewItem(court)).collect(Collectors.toList());
	}

	@Override
	public IOverviewItemLocator<Court> getItemLocator() {
		return new CourtOverviewItemLocator();
	}

	protected List<Court> getOverviewCourts() {
		return BundleUtil.getService(IServiceDao.class).getLatestElements(Court.class, 10);
	}

	public static OverviewItem<Court> createOverviewItem(final Court court) {
		return new OverviewItem<Court>(court, court.getName());
	}
}
