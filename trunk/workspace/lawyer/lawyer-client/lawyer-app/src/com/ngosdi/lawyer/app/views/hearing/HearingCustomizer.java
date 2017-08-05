package com.ngosdi.lawyer.app.views.hearing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ngosdi.lawyer.app.views.common.TitledSeparator;
import com.ngosdi.lawyer.app.views.common.customizer.AbstractCustomizer;
import com.ngosdi.lawyer.app.views.common.customizer.IValidationSupport;
import com.ngosdi.lawyer.app.views.common.customizer.ObjectSeekComposite;
import com.ngosdi.lawyer.app.views.common.customizer.SearchContext;
import com.ngosdi.lawyer.app.views.common.datalist.DataListModel;
import com.ngosdi.lawyer.app.views.common.proxy.ProxyFactory;
import com.ngosdi.lawyer.app.views.legalcase.CaseDataListModel;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Hearing;
import com.ngosdi.lawyer.beans.HearingStatus;

public class HearingCustomizer extends AbstractCustomizer<Hearing> {

	private final HearingCustomizerModel customizerModel;

	public HearingCustomizer(final Hearing hearing) {
		super(hearing, "Nouvelle audience", "Cette fenêtre permet de créer une nouvelle audience");
		customizerModel = ProxyFactory.createProxy(new HearingCustomizerModel(hearing));
	}

	@Override
	public Composite createArea(final Composite parent, final int style) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));

		final Label caseLabel = new Label(composite, SWT.NONE);
		caseLabel.setText("Affaire:");

		final LabelProvider labelProvider = new LabelProvider() {
			@Override
			public String getText(final Object element) {
				final Case legalCase = (Case) element;
				return legalCase.getCaseId();
			}
		};
		final DataListModel caseDataListModel = new CaseDataListModel(parent);
		final String title = "Recherche Affaire";
		final String description = "Cette fenetre permet de rechercher une affaire";
		final SearchContext searchContext = new SearchContext(caseDataListModel, labelProvider, title, description);
		final ObjectSeekComposite caseSeekComposite = new ObjectSeekComposite(composite, searchContext);
		caseSeekComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		bindingSupport.bindObjectSeekComposite(customizerModel, "case", caseSeekComposite, IValidationSupport.NOT_EMPTY_VALIDATOR);

		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 2, 1));

		new TitledSeparator(composite, "Audience").setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));

		final Label juridictionLabel = new Label(composite, SWT.NONE);
		juridictionLabel.setText("Juridiction: ");
		final Text juridictionText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "courtName", juridictionText);
		final GridData gridData = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
		gridData.widthHint = 150;
		juridictionText.setLayoutData(gridData);
		juridictionText.setEditable(false);
		caseSeekComposite.addValueChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				if (evt.getPropertyName() == ObjectSeekComposite.VALUE_PROPERTY) {
					final Case legalCase = (Case) evt.getNewValue();
					juridictionText.setText(legalCase.getCourt().getName());
				}
			}

		});

		final Label dateLabel = new Label(composite, SWT.NONE);
		dateLabel.setText("Date:");

		final DateTime date = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		date.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		bindingSupport.bindDateTimeChooserComboWidget(customizerModel, "date", date, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label statusDetailLabel = new Label(composite, SWT.NONE);
		statusDetailLabel.setText("Pour: ");
		final Text statusDetailText = new Text(composite, SWT.BORDER);
		statusDetailText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		bindingSupport.bindText(customizerModel, "statusDetail", statusDetailText, IValidationSupport.NOT_EMPTY_VALIDATOR);
		statusDetailText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

		final Label statusLabel = new Label(composite, SWT.NONE);
		statusLabel.setText("Statut:");

		final ComboViewer statusComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		final Combo combo = statusComboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		statusComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		statusComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((HearingStatus) element).getName();
			}
		});
		statusComboViewer.setInput(HearingStatus.values());
		bindingSupport.bindComboViewer(customizerModel, "status", statusComboViewer, IValidationSupport.NOT_EMPTY_VALIDATOR);

		new TitledSeparator(composite, "Substitution").setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));

		final Label professionalLabel = new Label(composite, SWT.NONE);
		professionalLabel.setText("Confrère: ");
		final Text professionalText = new Text(composite, SWT.BORDER);
		professionalText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

		final Label memberLabel = new Label(composite, SWT.NONE);
		memberLabel.setText("Membre:");

		final Text memberText = new Text(composite, SWT.BORDER);
		memberText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		new TitledSeparator(composite, "Autres").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 4, 1));

		final Label commentLabel = new Label(composite, SWT.NONE);
		commentLabel.setText("Commentaire:");

		final Text commentText = new Text(composite, SWT.BORDER);
		commentText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		bindingSupport.bindText(customizerModel, "comment", commentText);

		return composite;
	}

	@Override
	public void validateUpdate() {
		customizerModel.validate();
	}

	@Override
	public void cancelUpdate() {
		// Ask the user to confirm the cancellation
	}
}
