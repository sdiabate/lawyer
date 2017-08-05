package com.ngosdi.lawyer.app.views.legalcase;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.ngosdi.lawyer.app.Activator;
import com.ngosdi.lawyer.app.views.client.AdversaryOverviewItemLocator;
import com.ngosdi.lawyer.app.views.client.ClientOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.client.ClientOverviewItemLocator;
import com.ngosdi.lawyer.app.views.court.CourtOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.court.CourtOverviewItemLocator;
import com.ngosdi.lawyer.app.views.detail.AbstractDetailCompositeProvider;
import com.ngosdi.lawyer.app.views.hearing.HearingOverviewGroupProvider;
import com.ngosdi.lawyer.app.views.overview.HyperlinkLabelCreator;
import com.ngosdi.lawyer.app.views.overview.OverviewGroupComposite;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Hearing;

public class CaseDetailCompositeProvider extends AbstractDetailCompositeProvider<Case> {

	private final Map<Case, CaseDetail> caseDetails;

	public CaseDetailCompositeProvider(final String id, final Map<Case, CaseDetail> caseDetails) {
		super(id);
		this.caseDetails = caseDetails;
	}

	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Case;
	}

	@Override
	public Composite createComposite(final Composite parent, final Case item) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 100;
		composite.setLayout(layout);

		createColumn1(composite, item);
		createColumn2(composite, item);

		return composite;
	}

	public Map<Case, CaseDetail> getCaseDetails() {
		return caseDetails;
	}

	private void createColumn1(final Composite parent, final Case item) {

		final Composite composite = createColumnComposite(parent);

		final Composite idComposite = new Composite(composite, SWT.NONE);
		idComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		new Label(idComposite, SWT.NONE).setText("Affaire numéro ");
		new Label(idComposite, SWT.NONE).setText(item.getCaseId());
		new Label(idComposite, SWT.NONE).setText("instruite le");
		new Label(idComposite, SWT.NONE).setText(Activator.DATE_FORMAT.format(item.getStartDate()));
		new Label(idComposite, SWT.NONE).setText(", objet: ");
		new Label(idComposite, SWT.NONE).setText(item.getSubject() != null ? item.getSubject() : "***TBD***");

		final Composite clientComposite = new Composite(composite, SWT.NONE);
		clientComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		new Label(clientComposite, SWT.NONE).setText("Pour");
		HyperlinkLabelCreator.create(clientComposite, null, ClientOverviewGroupProvider.createOverviewItem(item.getClient()), new ClientOverviewItemLocator());
		new Label(clientComposite, SWT.NONE).setText("en qualité de");
		new Label(clientComposite, SWT.NONE).setText(item.getClientNature().getNatureName());

		final Composite oppositeComposite = new Composite(composite, SWT.NONE);
		oppositeComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		new Label(oppositeComposite, SWT.NONE).setText("Contre");
		HyperlinkLabelCreator.create(oppositeComposite, null, ClientOverviewGroupProvider.createOverviewItem(item.getOpposite()), new AdversaryOverviewItemLocator());
		new Label(oppositeComposite, SWT.NONE).setText("défendu(e) par ***TBD***");

		final Composite courtComposite = new Composite(composite, SWT.NONE);
		courtComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		new Label(courtComposite, SWT.NONE).setText("Juridiction");
		HyperlinkLabelCreator.create(courtComposite, null, CourtOverviewGroupProvider.createOverviewItem(item.getCourt()), new CourtOverviewItemLocator());
		new Label(courtComposite, SWT.NONE).setText(", section");
		new Label(courtComposite, SWT.NONE).setText(item.getSection().getSectionName());

		final Composite feesComposite = new Composite(composite, SWT.NONE);
		feesComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		new Label(feesComposite, SWT.NONE).setText("Honoraires: ");
		new Label(feesComposite, SWT.NONE).setText(String.valueOf(item.getHonorary()));

		final Composite commentComposite = new Composite(composite, SWT.NONE);
		commentComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		new Label(commentComposite, SWT.NONE).setText("Commentaires: ");
		new Label(feesComposite, SWT.NONE).setText(item.getComment() != null ? item.getComment() : "");
	}

	private void createColumn2(final Composite parent, final Case item) {
		final Composite composite = createColumnComposite(parent);
		((GridLayout) composite.getLayout()).verticalSpacing = 20;
		createComingHearingComposite(composite, item);
		createClosedHearingComposite(composite, item);
	}

	private Composite createColumnComposite(final Composite parent) {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		columnComposite.setLayout(new GridLayout());
		return columnComposite;
	}

	private void createComingHearingComposite(final Composite parent, final Case caze) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL));
		composite.setLayout(new GridLayout());
		final Label label = new Label(composite, SWT.NONE);
		label.setText("Audiences à venir");

		if (!caseDetails.get(caze).getComingHearings().isEmpty()) {
			new OverviewGroupComposite(composite, new HearingOverviewGroupProvider() {
				@Override
				protected java.util.List<Hearing> getOverviewHearings() {
					return caseDetails.get(caze).getComingHearings();
				};
			});
		} else {
			label.setText(label.getText() + ": aucun");
		}
	}

	private void createClosedHearingComposite(final Composite parent, final Case caze) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL));
		composite.setLayout(new GridLayout());
		final Label label = new Label(composite, SWT.NONE);
		label.setText("Audiences passées");

		if (!caseDetails.get(caze).getClosedHearings().isEmpty()) {
			new OverviewGroupComposite(composite, new HearingOverviewGroupProvider() {
				@Override
				protected java.util.List<Hearing> getOverviewHearings() {
					return caseDetails.get(caze).getClosedHearings();
				};
			});
		} else {
			label.setText(label.getText() + ": aucun");
		}
	}

}
