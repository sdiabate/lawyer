package com.ngosdi.lawyer.app.views.client;

import java.util.List;
import java.util.stream.Collectors;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.views.overview.IOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.overview.IOverviewItemLocator;
import com.ngosdi.lawyer.app.views.overview.OverviewItem;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.ClientType;
import com.ngosdi.lawyer.services.IServiceDao;

public class ClientOverviewGroupProvider implements IOverviewGroupProvider<Client> {

	@Override
	public String getTitle() {
		return "Clients recemment enregistrés";
	}

	@Override
	public List<OverviewItem<Client>> getOverviewItems() {
		return getOverviewClients().stream().filter(client -> client.getClientType() == ClientType.CLIENT).map(client -> createOverviewItem(client)).collect(Collectors.toList());
	}

	@Override
	public IOverviewItemLocator<Client> getItemLocator() {
		return new ClientOverviewItemLocator();
	}

	protected List<Client> getOverviewClients() {
		return BundleUtil.getService(IServiceDao.class).getLatestElements(Client.class, 10);
	}

	public static OverviewItem<Client> createOverviewItem(final Client client) {
		return new OverviewItem<Client>(client, client.getFirstName() + " " + client.getLastName());
	}
}
