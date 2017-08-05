package com.ngosdi.lawyer.app.views.court;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ngosdi.lawyer.app.views.common.TitledSeparator;
import com.ngosdi.lawyer.app.views.common.customizer.AbstractCustomizer;
import com.ngosdi.lawyer.app.views.common.customizer.IValidationSupport;
import com.ngosdi.lawyer.app.views.common.proxy.ProxyFactory;
import com.ngosdi.lawyer.beans.Court;
import com.ngosdi.lawyer.beans.CourtType;

public class CourtCustomizer extends AbstractCustomizer<Court> {

	private final CourtCustomizerModel customizerModel;

	public CourtCustomizer(final Court court) {
		super(court, "Nouvelle juridiction", "Cette fenêtre permet de saisir les informations des juridictions auprés desquelles sont enregistrées vos affaires.");
		customizerModel = ProxyFactory.createProxy(new CourtCustomizerModel(court));
	}

	@Override
	public Composite createArea(final Composite parent, final int style) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));

		new TitledSeparator(composite, "Juridiction").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 4, 1));

		final Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Nom:");
		final Text nameText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "name", nameText, IValidationSupport.NOT_EMPTY_VALIDATOR);
		final GridData gridData = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
		gridData.widthHint = 150;
		nameText.setLayoutData(gridData);

		final Label typeLabel = new Label(composite, SWT.NONE);
		typeLabel.setText("Type:");

		final ComboViewer typeCombo = new ComboViewer(composite, SWT.READ_ONLY);
		typeCombo.getCombo().setLayoutData(gridData);

		typeCombo.setContentProvider(ArrayContentProvider.getInstance());
		typeCombo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (CourtType.TRIBUNAL.equals(element)) {
					return "Tribunal";
				}
				if (CourtType.COUR.equals(element)) {
					return "Cour";
				}
				if (CourtType.COURS_SUPREME.equals(element)) {
					return "Cour suprème";
				}
				if (CourtType.CONSEIL_ETAT.equals(element)) {
					return "Conseil d'Etat";
				}
				if (CourtType.TRIBUNAL_ADMINISTRATIF.equals(element)) {
					return "Tribunal Administratif";
				}
				return "Tribunal";
			}
		});
		typeCombo.setInput(CourtType.values());
		bindingSupport.bindComboViewer(customizerModel, "type", typeCombo, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label adresseLabel = new Label(composite, SWT.NONE);
		adresseLabel.setText("Adresse:");
		final Text addressText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "address", addressText, IValidationSupport.NOT_EMPTY_VALIDATOR);
		addressText.setLayoutData(gridData);

		final Label cityLabel = new Label(composite, SWT.NONE);
		cityLabel.setText("Ville:");

		final Text cityText = new Text(composite, SWT.BORDER);
		cityText.setLayoutData(gridData);
		bindingSupport.bindText(customizerModel, "city", cityText, IValidationSupport.NOT_EMPTY_VALIDATOR);

		new TitledSeparator(composite, "Contacts").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 4, 1));

		final Label tel1Label = new Label(composite, SWT.NONE);
		tel1Label.setText("Tel :");
		final Text telText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "tel", telText, IValidationSupport.NOT_EMPTY_VALIDATOR);
		telText.setLayoutData(gridData);

		final Label emailLabel = new Label(composite, SWT.NONE);
		emailLabel.setText("email:");
		// TODO bind email
		final Text emailText = new Text(composite, SWT.BORDER);
		// bindingSupport.bindText(object, "landPhone", sectionText);
		emailText.setLayoutData(gridData);

		final Label faxLabel = new Label(composite, SWT.NONE);
		faxLabel.setText("Fax:");

		final Text faxText = new Text(composite, SWT.BORDER);
		faxText.setLayoutData(gridData);
		bindingSupport.bindText(customizerModel, "fax", faxText, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label lblCommentaire = new Label(composite, SWT.NONE);
		lblCommentaire.setText("Commentaire:");
		// TODO bind comments
		final Text commentaireText = new Text(composite, SWT.BORDER);
		commentaireText.setLayoutData(gridData);

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
