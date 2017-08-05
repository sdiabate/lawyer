package com.ngosdi.lawyer.app.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ngosdi.lawyer.app.web.model.ClientCustomizerModel;
import com.ngosdi.lawyer.app.web.model.EIdCardType;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.ClientType;
import com.ngosdi.lawyer.beans.Gender;
import com.ngosdi.lawyer.services.IServiceDao;

@Controller
@Lazy
@SessionAttributes(value = "client") // To add model attributes to the session, see SessionStatus.complete()
public class ClientController {

	@Autowired
	private IServiceDao serviceDao;

	@Autowired
	@Qualifier("clientValidator")
	private Validator validator;

	@InitBinder
	private void initBinder(final WebDataBinder binder) {
		binder.setValidator(validator);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
	}

	@ModelAttribute(value = "genders")
	private Gender[] genders() {
		return Gender.values();
	}

	@ModelAttribute(value = "idCardTypes")
	private EIdCardType[] idCardTypes() {
		return EIdCardType.values();
	}

	@ModelAttribute(value = "client")
	private ClientCustomizerModel getClient() {
		return new ClientCustomizerModel(new Client(ClientType.CLIENT));
	}

	@RequestMapping(value = "/clientsList", method = RequestMethod.GET)
	public ModelAndView display(final ModelMap modelMap, final SessionStatus sessionStatus) {
		final List<Client> clients = serviceDao.getAll(Client.class);
		sessionStatus.setComplete();
		return new ModelAndView("clientsList", "clients", clients.stream().map(c -> new ClientCustomizerModel(c)).collect(Collectors.toList()));
	}

	@RequestMapping(value = "/clientCreationForm", method = RequestMethod.GET)
	public ModelAndView creationForm(final ModelMap modelMap) {
		return new ModelAndView("clientCreation");
	}

	@RequestMapping(params = "save", value = "/clientCreation", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute(value = "client") @Validated final ClientCustomizerModel customizerModel, final BindingResult bindingResult, final ModelMap modelMap, final SessionStatus sessionStatus) {
		if (!bindingResult.hasErrors()) {
			final Client client = new Client(ClientType.CLIENT);
			customizerModel.validate(client);
			serviceDao.save(client);
			return display(modelMap, sessionStatus);
		}
		return new ModelAndView("clientCreation");
	}

	@RequestMapping(params = "cancel", value = "/clientCreation", method = RequestMethod.POST)
	public ModelAndView cancelCreation(final ModelMap modelMap, final SessionStatus sessionStatus) {
		return display(modelMap, sessionStatus);
	}

	@RequestMapping(value = "/clientUpdate", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam(value = "idClient") final Long idClient, final ModelMap modelMap) {
		modelMap.put("client", new ClientCustomizerModel(serviceDao.find(Client.class, idClient)));
		return new ModelAndView("clientCreation");
	}

	@RequestMapping(value = "/clientDeletion", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "idClient") final Long idClient, final ModelMap modelMap, final SessionStatus sessionStatus) {
		serviceDao.delete(Client.class, idClient);
		return display(modelMap, sessionStatus);
	}

	@RequestMapping(value = "/randomClient", method = RequestMethod.GET)
	public ModelAndView randomClient(final ModelMap modelMap) {
		final List<Client> clients = serviceDao.getAll(Client.class);
		if (!clients.isEmpty()) {
			final Client selectedClient = clients.get(new Random().nextInt(clients.size()));
			return new ModelAndView("randomClient", "client", new ClientCustomizerModel(selectedClient));
		}
		return new ModelAndView("randomClient");
	}

	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public ModelAndView clientsManagement(final ModelMap modelMap) {
		return new ModelAndView("clients");
	}
}
