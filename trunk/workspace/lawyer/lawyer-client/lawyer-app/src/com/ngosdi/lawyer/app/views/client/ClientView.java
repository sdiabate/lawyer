package com.ngosdi.lawyer.app.views.client;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.IApplicationEvent;
import com.ngosdi.lawyer.app.views.PartView;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.ClientType;
import com.ngosdi.lawyer.beans.Hearing;
import com.ngosdi.lawyer.services.IServiceDao;

public class ClientView extends PartView<Client> {

	@Override
	protected void createGui(final Composite parent) {
		parent.setLayout(new FillLayout());

		dataListModel = new ClientDataListModel(parent, geClientType());

		final Map<Client, ClientDetail> clientDetails = ((ClientDataListModel) dataListModel).getClients().stream().map(client -> new ClientDetail(client))
				.collect(Collectors.toMap(ClientDetail::getClient, clientDetail -> clientDetail));

		updateWithComingHearings(clientDetails);
		updateWithClosedHearings(clientDetails);
		updateWithInProgressCases(clientDetails);
		updateWithClosedCases(clientDetails);

		partViewService.addDetailCompositeProvider(new ClientDetailCompositeProvider(currentPart.getElementId(), clientDetails, geClientType()));
	}

	protected ClientType geClientType() {
		return ClientType.CLIENT;
	}

	private void updateWithInProgressCases(final Map<Client, ClientDetail> clientDetails) {
		updateWithCases(clientDetails, caze -> true, cazesPerClient -> cazesPerClient.forEach((client, cazes) -> {
			clientDetails.get(client).getInProgressCases().addAll(cazes);
		}));
	}

	private void updateWithClosedCases(final Map<Client, ClientDetail> clientDetails) {
		updateWithCases(clientDetails, caze -> true, cazesPerClient -> cazesPerClient.forEach((client, cazes) -> {
			clientDetails.get(client).getClosedCases().addAll(cazes);
		}));
	}

	private void updateWithCases(final Map<Client, ClientDetail> clientDetails, final Predicate<Case> condition, final Consumer<Map<Client, List<Case>>> consumer) {
		try (final Stream<Case> stream = BundleUtil.getService(IServiceDao.class).getAllAsStream(Case.class)) {
			final Map<Client, List<Case>> cazesPerClient = stream
					.filter(caze -> condition.test(caze) && clientDetails.keySet().contains(caze.getClient())
							&& ChronoUnit.DAYS.between(new Date(caze.getStartDate().getTime()).toInstant(), Instant.now()) < 365 * 10)
					.sorted((caze1, caze2) -> caze2.getStartDate().compareTo(caze1.getStartDate())).collect(Collectors.groupingBy(Case::getClient));
			consumer.accept(cazesPerClient);
		}
	}

	private void updateWithComingHearings(final Map<Client, ClientDetail> clientDetails) {
		updateWithHearings(clientDetails, hearing -> hearing.getDate().after(new Date()), hearingPerClient -> hearingPerClient.forEach((client, hearings) -> {
			clientDetails.get(client).getComingHearings().addAll(hearings);
		}));
	}

	private void updateWithClosedHearings(final Map<Client, ClientDetail> clientDetails) {
		updateWithHearings(clientDetails, hearing -> hearing.getDate().before(new Date()), hearingPerClient -> hearingPerClient.forEach((client, hearings) -> {
			clientDetails.get(client).getClosedHearings().addAll(hearings);
		}));
	}

	private void updateWithHearings(final Map<Client, ClientDetail> clientDetails, final Predicate<Hearing> condition, final Consumer<Map<Client, List<Hearing>>> consumer) {
		try (final Stream<Hearing> stream = BundleUtil.getService(IServiceDao.class).getAllAsStream(Hearing.class)) {
			final Map<Client, List<Hearing>> hearingPerClient = stream.filter(hearing -> clientDetails.keySet().contains(hearing.getLegalCase().getClient()) && condition.test(hearing))
					.sorted((h1, h2) -> h1.getDate().compareTo(h2.getDate())).collect(Collectors.groupingBy(hearing -> hearing.getLegalCase().getClient()));
			consumer.accept(hearingPerClient);
		}
	}

	@Inject
	@Optional
	private void clientCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Client client) {
		if (client.getClientType() == geClientType()) {
			final Map<Client, ClientDetail> clientDetails = ((ClientDetailCompositeProvider) partViewService.getDetailCompositeProvider(currentPart.getElementId())).getClientDetails();
			clientDetails.put(client, new ClientDetail(client));
		}
	}

	@Inject
	@Optional
	private void caseCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Case caze) {
		final Map<Client, ClientDetail> clientDetails = ((ClientDetailCompositeProvider) partViewService.getDetailCompositeProvider(currentPart.getElementId())).getClientDetails();
		final Client client = caze.getClient();
		if (client.getClientType() == geClientType()) {
			clientDetails.get(client).getInProgressCases().add(caze);
			clientDetails.get(client).getClosedCases().add(caze);// TODO
		}
	}

	@Inject
	@Optional
	private void hearingCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Hearing hearing) {
		final Map<Client, ClientDetail> clientDetails = ((ClientDetailCompositeProvider) partViewService.getDetailCompositeProvider(currentPart.getElementId())).getClientDetails();
		final Client client = hearing.getLegalCase().getClient();
		if (client.getClientType() == geClientType()) {
			if (hearing.getDate().after(new Date())) {
				clientDetails.get(client).getComingHearings().add(hearing);
			} else {
				clientDetails.get(client).getClosedHearings().add(hearing);
			}
		}
	}

}