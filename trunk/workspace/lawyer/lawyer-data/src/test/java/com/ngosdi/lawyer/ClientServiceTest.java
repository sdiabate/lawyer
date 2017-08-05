package com.ngosdi.lawyer;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.IdentityCard;
import com.ngosdi.lawyer.services.IServiceDao;

public class ClientServiceTest {

//	@Test
//	public void createClient() {
//
//		final Application app = new Application(Thread.currentThread().getContextClassLoader().getResource("."), "dev");
//		app.init();
//		final IServiceDao service = app.getServiceDao();
//
//		service.save(newClient());
//
//		final List<Client> clients = service.getAll(Client.class);
//
//		Assert.assertNotNull(clients);
//		Assert.assertEquals(1, clients.size());
//
//		final Client client = clients.get(0);
//
//		Assert.assertNotNull(client);
//		Assert.assertEquals("Eva", client.getFirstName());
//		Assert.assertEquals("Markeline", client.getLastName());
//		Assert.assertEquals("South", client.getRegion());
//
//		final IdentityCard idCard = client.getIdentityCard();
//
//		Assert.assertNotNull(idCard);
//		Assert.assertEquals("PASSPORT", idCard.getType());
//
//		Assert.assertEquals(2, client.getPhones().size());
//		Assert.assertEquals("98765266", client.getPhones().get("MOBILE"));
//		Assert.assertEquals("85634242", client.getPhones().get("LAND"));
//	}
//
//	private Client newClient() {
//		final Client client = new Client();
//		client.setFirstName("Eva");
//		client.setLastName("Markeline");
//		client.setAddress("34 Av Emile Kosso, 1233");
//		client.setBirthdate(new Date());
//		client.setRegion("South");
//		client.setCountry("France");
//		client.setCity("Aix");
//		client.setPost("Lawyer");
//		client.setFax("12356789");
//		client.getPhones().put("MOBILE", "98765266");
//		client.getPhones().put("LAND", "85634242");
//		final IdentityCard idCard = new IdentityCard();
//		idCard.setNumber("867768779989090A");
//		idCard.setCountry("France");
//		idCard.setType("PASSPORT");
//		idCard.setDeliveryDate(new Date());
//		client.setIdentityCard(idCard);
//		return client;
//	}
}
