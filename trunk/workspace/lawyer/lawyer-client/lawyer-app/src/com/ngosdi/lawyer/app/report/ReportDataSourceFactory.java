package com.ngosdi.lawyer.app.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.views.client.ClientCustomizerModel;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.Gender;
import com.ngosdi.lawyer.beans.IdentityCard;
import com.ngosdi.lawyer.services.IServiceDao;

public class ReportDataSourceFactory {

	public static JRDataSource createClientDatasource() {
		final List<Client> clients = BundleUtil.getService(IServiceDao.class).getAll(Client.class);
		final List<ClientCustomizerModel> clientModels = new ArrayList<ClientCustomizerModel>(clients.size());
		for (final Client client : clients) {
			clientModels.add(new ClientCustomizerModel(client));
		}
		return new JRBeanCollectionDataSource(clientModels);
	}

	public static List<ClientCustomizerModel> getClients() {
		final List<ClientCustomizerModel> clients = new ArrayList<ClientCustomizerModel>();
		clients.add(new ClientCustomizerModel(createClient()));
		clients.add(new ClientCustomizerModel(createClient()));
		clients.add(new ClientCustomizerModel(createClient()));
		clients.add(new ClientCustomizerModel(createClient()));
		return clients;
	}

	private static Client createClient() {
		final Client client = new Client();
		client.setFirstName("Eva");
		client.setLastName("Markeline");
		client.setGender(Gender.FEMALE);
		client.setAddress("34 Av Emile Kosso, 1233");
		client.setBirthdate(new Date());
		client.setRegion("South");
		client.setCountry("France");
		client.setCity("Aix");
		client.setPost("Lawyer");
		client.setFax("12356789");
		client.getPhones().put("MOBILE", "98765266");
		client.getPhones().put("LAND", "85634242");
		final IdentityCard idCard = new IdentityCard();
		idCard.setNumber("867768779989090A");
		idCard.setCountry("France");
		idCard.setType("PASSPORT");
		idCard.setDeliveryDate(new Date());
		client.setIdentityCard(idCard);
		return client;
	}
}
