package com.ngosdi.lawyer.app.views.legalcase;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.IApplicationEvent;
import com.ngosdi.lawyer.app.views.PartView;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Hearing;
import com.ngosdi.lawyer.services.IServiceDao;

public class CaseView extends PartView<Case> {

	@Override
	protected void createGui(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		dataListModel = new CaseDataListModel(parent);

		final Map<Case, CaseDetail> caseDetails = ((CaseDataListModel) dataListModel).getCases().stream().map(caze -> new CaseDetail(caze))
				.collect(Collectors.toMap(CaseDetail::getCaze, caseDetail -> caseDetail));

		updateWithComingHearings(caseDetails);
		updateWithClosedHearings(caseDetails);

		partViewService.addDetailCompositeProvider(new CaseDetailCompositeProvider(currentPart.getElementId(), caseDetails));
	}

	private void updateWithComingHearings(final Map<Case, CaseDetail> caseDetails) {
		updateWithHearings(caseDetails, hearing -> hearing.getDate().after(new Date()), hearingPerCase -> hearingPerCase.forEach((client, hearings) -> {
			caseDetails.get(client).getComingHearings().addAll(hearings);
		}));
	}

	private void updateWithClosedHearings(final Map<Case, CaseDetail> caseDetails) {
		updateWithHearings(caseDetails, hearing -> hearing.getDate().before(new Date()), hearingPerCase -> hearingPerCase.forEach((client, hearings) -> {
			caseDetails.get(client).getClosedHearings().addAll(hearings);
		}));
	}

	private void updateWithHearings(final Map<Case, CaseDetail> caseDetails, final Predicate<Hearing> condition, final Consumer<Map<Case, List<Hearing>>> consumer) {
		try (final Stream<Hearing> stream = BundleUtil.getService(IServiceDao.class).getAllAsStream(Hearing.class)) {
			final Map<Case, List<Hearing>> hearingPerCase = stream.filter(hearing -> caseDetails.keySet().contains(hearing.getLegalCase()) && condition.test(hearing))
					.sorted((h1, h2) -> h1.getDate().compareTo(h2.getDate())).collect(Collectors.groupingBy(Hearing::getLegalCase));
			consumer.accept(hearingPerCase);
		}
	}

	@Inject
	@Optional
	private void caseCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Case caze) {
		final Map<Case, CaseDetail> caseDetails = ((CaseDetailCompositeProvider) partViewService.getDetailCompositeProvider(currentPart.getElementId())).getCaseDetails();
		caseDetails.put(caze, new CaseDetail(caze));
	}

	@Inject
	@Optional
	private void hearingCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Hearing hearing) {
		final Map<Case, CaseDetail> caseDetails = ((CaseDetailCompositeProvider) partViewService.getDetailCompositeProvider(currentPart.getElementId())).getCaseDetails();
		final Case caze = hearing.getLegalCase();
		if (hearing.getDate().after(new Date())) {
			caseDetails.get(caze).getComingHearings().add(hearing);
		} else {
			caseDetails.get(caze).getClosedHearings().add(hearing);
		}
	}

}