package com.ngosdi.lawyer.app.views.client;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.ngosdi.lawyer.app.Activator;
import com.ngosdi.lawyer.app.views.detail.AbstractDetailCompositeProvider;
import com.ngosdi.lawyer.app.views.hearing.HearingOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.legalcase.CaseOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.overview.OverviewGroupComposite;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.ClientType;
import com.ngosdi.lawyer.beans.Hearing;

public class ClientDetailCompositeProvider extends AbstractDetailCompositeProvider<Client> {

	private final Map<Client, ClientDetail> clientDetails;
	private final ClientType clientType;

	public ClientDetailCompositeProvider(final String id, final Map<Client, ClientDetail> clientDetails, final ClientType clientType) {
		super(id);
		this.clientDetails = clientDetails;
		this.clientType = clientType;
	}

	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Client && ((Client) item).getClientType() == clientType;
	}

	@Override
	public Composite createComposite(final Composite parent, final Client client) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 100;
		composite.setLayout(layout);

		createColumn1(composite, client);
		createColumn2(composite, client);
		createColumn3(composite, client);

		return composite;
	}

	public Map<Client, ClientDetail> getClientDetails() {
		return clientDetails;
	}

	private void createColumn1(final Composite parent, final Client client) {
		final Composite composite = createColumnComposite(parent);

		final Label civilStatusLabel = new Label(composite, SWT.NONE);
		civilStatusLabel.setText(getCivilStatus(client));

		final Label contactLabel = new Label(composite, SWT.NONE);
		contactLabel.setText(getContacts(client));

		final Label idCardLabel = new Label(composite, SWT.NONE);
		idCardLabel.setText(getIdentity(client));
	}

	private void createColumn2(final Composite parent, final Client client) {
		final Composite composite = createColumnComposite(parent);
		createComingHearingComposite(composite, client);
		createClosedHearingComposite(composite, client);
	}

	private void createColumn3(final Composite parent, final Client client) {
		final Composite composite = createColumnComposite(parent);
		createInProgressCasesComposite(composite, client);
		createClosedCasesComposite(composite, client);
	}

	private void createComingHearingComposite(final Composite parent, final Client client) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL));
		composite.setLayout(new GridLayout());
		final Label label = new Label(composite, SWT.NONE);
		label.setText("Audiences à venir");

		if (!clientDetails.get(client).getComingHearings().isEmpty()) {
			new OverviewGroupComposite(composite, new HearingOverviewGroupProvider() {
				@Override
				protected java.util.List<Hearing> getOverviewHearings() {
					return clientDetails.get(client).getComingHearings();
				};
			});
		} else {
			label.setText(label.getText() + ": aucun");
		}
	}

	private void createClosedHearingComposite(final Composite parent, final Client client) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL));
		composite.setLayout(new GridLayout());
		final Label label = new Label(composite, SWT.NONE);
		label.setText("Audiences passées");

		if (!clientDetails.get(client).getClosedHearings().isEmpty()) {
			new OverviewGroupComposite(composite, new HearingOverviewGroupProvider() {
				@Override
				protected java.util.List<Hearing> getOverviewHearings() {
					return clientDetails.get(client).getClosedHearings();
				};
			});
		} else {
			label.setText(label.getText() + ": aucun");
		}
	}

	private void createInProgressCasesComposite(final Composite parent, final Client client) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL));
		composite.setLayout(new GridLayout());
		final Label label = new Label(composite, SWT.NONE);
		label.setText("Affaires en cours");

		if (!clientDetails.get(client).getInProgressCases().isEmpty()) {
			new OverviewGroupComposite(composite, new CaseOverviewGroupProvider() {
				@Override
				protected List<Case> getOverviewCases() {
					return clientDetails.get(client).getInProgressCases();
				}
			});
		} else {
			label.setText(label.getText() + ": aucun");
		}
	}

	private void createClosedCasesComposite(final Composite parent, final Client client) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL));
		composite.setLayout(new GridLayout());
		final Label label = new Label(composite, SWT.NONE);
		label.setText("Dernières affaires jugées");

		if (!clientDetails.get(client).getClosedCases().isEmpty()) {
			new OverviewGroupComposite(composite, new CaseOverviewGroupProvider() {
				@Override
				protected List<Case> getOverviewCases() {
					return clientDetails.get(client).getClosedCases();
				}
			});
		} else {
			label.setText(label.getText() + ": aucun");
		}
	}

	private Composite createColumnComposite(final Composite parent) {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		columnComposite.setLayout(new GridLayout());
		((GridLayout) columnComposite.getLayout()).verticalSpacing = 20;
		return columnComposite;
	}

	private String getCivilStatus(final Client client) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(client.getLastName());
		stringBuilder.append(" ");
		stringBuilder.append(client.getFirstName());
		stringBuilder.append("\n");
		stringBuilder.append("Né le ");
		if (client.getBirthdate() != null) {
			stringBuilder.append(Activator.DATE_FORMAT.format(client.getBirthdate()));
		}
		stringBuilder.append(" à ");
		stringBuilder.append(client.getCity());
		stringBuilder.append(" (");
		stringBuilder.append(client.getCountry());
		stringBuilder.append(")\n");
		return stringBuilder.toString();
	}

	private String getContacts(final Client client) {
		return "Adresse / Contacts: ";
	}

	private String getIdentity(final Client client) {
		return "Papiers d'identité:";
	}
}
