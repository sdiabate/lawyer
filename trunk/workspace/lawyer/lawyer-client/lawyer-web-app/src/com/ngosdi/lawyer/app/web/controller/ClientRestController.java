package com.ngosdi.lawyer.app.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ngosdi.lawyer.app.web.model.ClientCustomizerModel;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.ClientType;
import com.ngosdi.lawyer.services.IServiceDao;

@RestController
public class ClientRestController {

	@Autowired
	private IServiceDao serviceDao;

	@RequestMapping(value = "/allClients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClientCustomizerModel>> getAllClients() {
		final List<Client> clients = serviceDao.getAll(Client.class);
		final List<ClientCustomizerModel> models = clients.stream().map(c -> new ClientCustomizerModel(c)).collect(Collectors.toList());
		return new ResponseEntity<List<ClientCustomizerModel>>(models, HttpStatus.OK);
	}

	@RequestMapping(value = "/createClient", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createClient(@RequestBody final ClientCustomizerModel clientModel, final UriComponentsBuilder ucBuilder) {
		final Client client = new Client(ClientType.CLIENT);
		clientModel.validate(client);
		serviceDao.save(client);

		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("createClient/{id}").buildAndExpand(client.getId()).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateClient", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientCustomizerModel> updateClient(@RequestBody final ClientCustomizerModel clientModel) {
		final Client client = serviceDao.find(Client.class, clientModel.getId());
		if (client == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		clientModel.validate(client);
		serviceDao.save(client);
		return new ResponseEntity<ClientCustomizerModel>(clientModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteClient", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteClient(@RequestBody final Long idClient, final UriComponentsBuilder ucBuilder) {
		serviceDao.delete(Client.class, idClient);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
