/**
 *
 */
package com.ngosdi.lawyer.app.views.client;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.E4Service;
import com.ngosdi.lawyer.app.IApplicationEvent;
import com.ngosdi.lawyer.app.report.ReportDataSourceFactory;
import com.ngosdi.lawyer.app.views.common.EImage;
import com.ngosdi.lawyer.app.views.common.EPhoneType;
import com.ngosdi.lawyer.app.views.common.action.ContextualAction;
import com.ngosdi.lawyer.app.views.common.action.ContextualActionPathElement;
import com.ngosdi.lawyer.app.views.common.action.DataListActionEvent;
import com.ngosdi.lawyer.app.views.common.action.IAction;
import com.ngosdi.lawyer.app.views.common.customizer.CustomizerDialog;
import com.ngosdi.lawyer.app.views.common.customizer.DialogBox;
import com.ngosdi.lawyer.app.views.common.customizer.DocumentList;
import com.ngosdi.lawyer.app.views.common.datalist.ColumnDescriptor;
import com.ngosdi.lawyer.app.views.common.datalist.DataListActionModel;
import com.ngosdi.lawyer.app.views.common.datalist.DataListModel;
import com.ngosdi.lawyer.app.views.legalcase.CaseCustomizer;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.ClientType;
import com.ngosdi.lawyer.services.IServiceDao;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * @author user
 */
public class ClientDataListModel extends DataListModel {

	private List<Client> clients;

	public ClientDataListModel(final Composite parent, final ClientType clientType) {
		inititalize(parent, clientType);
	}

	private void inititalize(final Composite parent, final ClientType clientType) {

		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "firstName", "lastName", "identityCard/number", "fax", "phones[@name='MOBILE']", "phones[@name='LAND']" };

		columnDescriptors = new ColumnDescriptor[4];
		columnDescriptors[0] = new ColumnDescriptor("Nom", 0.30, 125);
		columnDescriptors[1] = new ColumnDescriptor("Prénom", 0.30, 125);
		columnDescriptors[2] = new ColumnDescriptor("Téléphone", 0.10, 125);
		columnDescriptors[3] = new ColumnDescriptor("Adresse", 0.30, 125);

		final IAction createAction = (event) -> {
			final Client client = new Client(clientType);
			final String title = clientType == ClientType.CLIENT ? "Nouveau client" : "Nouvel adversaire";
			final String description = String.format("Cette fenêtre permet de créer un %1s", clientType == ClientType.CLIENT ? "nouveau client" : "nouvel adversaire");
			final ClientCustomizer customizer = new ClientCustomizer(client, title, description);
			final DocumentList documentList = new DocumentList(client.getDocuments());
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Dialog.OK) {
				// Save to DB
				final Client savedClient = BundleUtil.getService(IServiceDao.class).save(client);
				((DataListActionEvent) event).getDataListModel().getDataList().add(savedClient);
				return savedClient;
			}
			return null;
		};

		final IAction editAction = (event) -> {
			final Client client = (Client) event.getTarget();
			final String title = clientType == ClientType.CLIENT ? "Edition d'un client" : "Edition d'un adversaire";
			final String description = String.format("Cette fenêtre permet d'éditer un %1s", clientType == ClientType.CLIENT ? "client" : "adversaire");
			final ClientCustomizer customizer = new ClientCustomizer(client, title, description);
			final DocumentList documentList = new DocumentList(client.getDocuments());
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);

			if (dialog.open() == Dialog.OK) {
				// Merge to DB
				return BundleUtil.getService(IServiceDao.class).save(client);
			}
			return null;
		};

		final IAction deleteAction = (event) -> {
			final Client client = (Client) event.getTarget();
			// Delete from DB
			BundleUtil.getService(IServiceDao.class).delete(client);
			((DataListActionEvent) event).getDataListModel().getDataList().remove(client);
			return client;
		};

		actionModel = new DataListActionModel(createAction, editAction, deleteAction, createDatalistMenuActions(parent), createReportActions());

		dataList = new WritableList(new ArrayList<Client>(retrieveClientByType(clientType)), Client.class) {
			@Override
			public boolean add(final Object element) {
				final Client client = (Client) element;
				if (client.getClientType() != clientType) {
					return false;
				}
				return super.add(element);
			}
		};

	}

	private List<Client> retrieveClientByType(final ClientType clientType) {
		final List<Client> allClients = BundleUtil.getService(IServiceDao.class).getAll(Client.class);
		clients = allClients.stream().filter(client -> client.getClientType() == clientType).collect(Collectors.toList());
		return clients;
	}

	public List<Client> getClients() {
		return clients;
	}

	private ContextualAction[] createDatalistMenuActions(final Composite parent) {
		final IAction newCaseAction = event -> {
			final Case caze = new Case();
			caze.setClient((Client) event.getTarget());
			final CaseCustomizer customizer = new CaseCustomizer(caze);
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Dialog.OK) {
				BundleUtil.getService(E4Service.class).getEventBroker().post(IApplicationEvent.ITEM_CREATED, caze);
				return BundleUtil.getService(IServiceDao.class).save(caze);
			}
			return null;
		};

		final IAction latestCasesAction = (event) -> {
			return null;
		};

		final ContextualActionPathElement[] newCasePath = new ContextualActionPathElement[] { new ContextualActionPathElement("Affaires", null),
				new ContextualActionPathElement("Nouvelle affaire", EImage.CREATE.getSwtImage()) };
		final ContextualActionPathElement[] latestCasesPath = new ContextualActionPathElement[] { new ContextualActionPathElement("Affaires", null),
				new ContextualActionPathElement("Affaires recentes", null) };

		final ContextualAction newCaseContextualAction = new ContextualAction(newCaseAction, newCasePath);
		final ContextualAction latestCasesContextualAction = new ContextualAction(latestCasesAction, latestCasesPath);
		final ContextualAction[] dataListMenuActions = new ContextualAction[] { newCaseContextualAction, latestCasesContextualAction };
		return dataListMenuActions;
	}

	private ContextualAction[] createReportActions() {
		final ContextualAction[] actions = new ContextualAction[2];

		final ContextualActionPathElement[] clientListPath = new ContextualActionPathElement[] { new ContextualActionPathElement("Liste des clients", null) };
		final IAction clientListAction = (event) -> {
			Display.getDefault().asyncExec(() -> {
				try {
					// final JasperPrint jasperPrint =
					// JasperFillManager.fillReport(uri.toURL().openStream(),
					// new HashMap<String, Object>(),
					// BundleUtil.getService(IServiceDao.class)
					// .getJdbcConnection());
					final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
					final URI uri = URI.create("platform:/plugin/lawyer-app/reports/clients_list2.jasper");
					final JasperPrint jasperPrint = JasperFillManager.fillReport(uri.toURL().openStream(), new HashMap<String, Object>(), ReportDataSourceFactory.createClientDatasource());
					JasperViewer.viewReport(jasperPrint, false);
					Thread.currentThread().setContextClassLoader(classLoader);
				} catch (JRException | IOException e) {
					// TODO LOGGER
					e.printStackTrace();
				}

			});
			return null;

		};
		actions[0] = new ContextualAction(clientListAction, clientListPath);

		final ContextualActionPathElement[] caseListPath = new ContextualActionPathElement[] { new ContextualActionPathElement("Liste des affaires par clients", null) };
		final IAction caseListAction = (event) -> {
			return null;
		};
		actions[1] = new ContextualAction(caseListAction, caseListPath);

		return actions;
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Client client = (Client) object;
			switch (column) {
				case 0:
					return client.getFirstName();
				case 1:
					return client.getLastName();
				case 2:
					return client.getPhones().get(EPhoneType.MOBILE.name());
				case 3:
					return client.getAddress();
			}
			return null;
		}
	}
}
