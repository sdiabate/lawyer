package com.ngosdi.lawyer.app.views.hearing;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ngosdi.lawyer.app.Activator;
import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.views.overview.IOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.overview.IOverviewItemLocator;
import com.ngosdi.lawyer.app.views.overview.OverviewItem;
import com.ngosdi.lawyer.beans.Hearing;
import com.ngosdi.lawyer.services.IServiceDao;

public class HearingOverviewGroupProvider implements IOverviewGroupProvider<Hearing> {

	@Override
	public String getTitle() {
		return "Planning des audiences";
	}

	@Override
	public List<OverviewItem<Hearing>> getOverviewItems() {
		return getOverviewHearings().stream().map(hearing -> new OverviewItem<Hearing>(hearing,
				String.format("%s %s du %s", hearing.getLegalCase().getClient().getLastName(), hearing.getLegalCase().getClient().getFirstName(), Activator.DATE_FORMAT.format(hearing.getDate()))))
				.collect(Collectors.toList());
	}

	@Override
	public IOverviewItemLocator<Hearing> getItemLocator() {
		return new HearingOverviewItemLocator();
	}

	protected List<Hearing> getOverviewHearings() {
		try (Stream<Hearing> stream = BundleUtil.getService(IServiceDao.class).getAllAsStream(Hearing.class)) {
			return stream.sorted((h1, h2) -> h1.getDate().compareTo(h2.getDate())).limit(10).collect(Collectors.toList());
		}
	}
}
