package com.ngosdi.lawyer.app.views.client;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ngosdi.lawyer.app.views.common.EIdCardType;
import com.ngosdi.lawyer.app.views.common.TitledSeparator;
import com.ngosdi.lawyer.app.views.common.customizer.AbstractCustomizer;
import com.ngosdi.lawyer.app.views.common.customizer.IValidationSupport;
import com.ngosdi.lawyer.app.views.common.proxy.ProxyFactory;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.Gender;

public class ClientCustomizer extends AbstractCustomizer<Client> {

	private final ClientCustomizerModel customizerModel;

	public ClientCustomizer(final Client client, final String title, final String description) {
		super(client, title, description);
		customizerModel = ProxyFactory.createProxy(new ClientCustomizerModel(client));
	}

	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(6, false);
		composite.setLayout(gridLayout);

		final Label genderLabel = new Label(composite, SWT.NONE);
		genderLabel.setText("Mr/Mme: ");
		final ComboViewer genderComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		genderComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		genderComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (Gender.MALE.equals(element)) {
					return "Mr";
				}
				return "Mme";
			}
		});
		genderComboViewer.setInput(Gender.values());
		bindingSupport.bindComboViewer(customizerModel, "gender", genderComboViewer, IValidationSupport.NOT_EMPTY_VALIDATOR);

		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 4, 1));

		final Label firstNameLabel = new Label(composite, SWT.NONE);
		firstNameLabel.setText("Nom: ");
		final Text firstNameText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "firstName", firstNameText, IValidationSupport.NOT_EMPTY_VALIDATOR);
		firstNameText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 5, 1));

		final Label lastNameLabel = new Label(composite, SWT.NONE);
		lastNameLabel.setText("Prénom: ");
		final Text lastNameText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "lastName", lastNameText, IValidationSupport.NOT_EMPTY_VALIDATOR);
		lastNameText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));

		final Label birthdateLabel = new Label(composite, SWT.NONE);
		birthdateLabel.setText("Date naissance: ");

		// final FormattedText birthdateText = new FormattedText(composite,
		// SWT.BORDER);
		// birthdateText.setFormatter(new DateFormatter(Locale.FRENCH));
		// bindingSupport.bindFormattedText(customizerModel, "birthdate",
		// birthdateText, IValidationSupport.NOT_EMPTY_VALIDATOR);
		// gridData = new GridData();
		// gridData.horizontalAlignment = GridData.FILL;
		// gridData.grabExcessHorizontalSpace = true;
		// birthdateText.getControl().setLayoutData(gridData);

		final DateTime birthDateCombo = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		birthDateCombo.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		final GridData gridData = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
		gridData.widthHint = 150;
		birthDateCombo.setLayoutData(gridData);
		bindingSupport.bindDateTimeChooserComboWidget(customizerModel, "birthdate", birthDateCombo, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label bornCityLabel = new Label(composite, SWT.NONE);
		bornCityLabel.setText("Ville: ");
		final Text bornCityText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "city", bornCityText, IValidationSupport.NOT_EMPTY_VALIDATOR);
		bornCityText.setLayoutData(gridData);

		final Label bornCountryLabel = new Label(composite, SWT.NONE);
		bornCountryLabel.setText("Pays: ");
		final Text bornCountryText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "country", bornCountryText, IValidationSupport.NOT_EMPTY_VALIDATOR);
		bornCountryText.setLayoutData(gridData);

		final Label postLabel = new Label(composite, SWT.NONE);
		postLabel.setText("Fonction: ");
		final Text postText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "post", postText);
		postText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));

		new TitledSeparator(composite, "Contact").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));

		final Label addressLabel = new Label(composite, SWT.NONE);
		addressLabel.setText("Adresse: ");
		final Text addressText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "address", addressText);
		addressText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));

		final Label mobileLabel = new Label(composite, SWT.NONE);
		mobileLabel.setText("Mobile: ");
		final Text mobileText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "mobilePhone", mobileText);
		mobileText.setLayoutData(gridData);

		final Label landLabel = new Label(composite, SWT.NONE);
		landLabel.setText("Fixe: ");
		final Text landText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "landPhone", landText);
		landText.setLayoutData(gridData);

		final Label faxLabel = new Label(composite, SWT.NONE);
		faxLabel.setText("Fax: ");
		final Text faxText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "fax", faxText);
		faxText.setLayoutData(gridData);

		new TitledSeparator(composite, "Document d'identité").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));

		final Label idCardTypeLabel = new Label(composite, SWT.NONE);
		idCardTypeLabel.setText("Type: ");
		final ComboViewer idCardTypeComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		idCardTypeComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		idCardTypeComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((EIdCardType) element).getLabel();
			}
		});
		idCardTypeComboViewer.setInput(EIdCardType.values());
		bindingSupport.bindComboViewer(customizerModel, "idCardType", idCardTypeComboViewer);
		idCardTypeComboViewer.getCombo().setLayoutData(gridData);

		final Label idCardNumberLabel = new Label(composite, SWT.NONE);
		idCardNumberLabel.setText("Numéro: ");
		idCardNumberLabel.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false));
		final Text idCardNumberText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "idCardNumber", idCardNumberText);
		idCardNumberText.setLayoutData(gridData);

		final Label idCardDateLabel = new Label(composite, SWT.NONE);
		idCardDateLabel.setText("Date: ");

		final DateTime dateChooserCombo = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		dateChooserCombo.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		dateChooserCombo.setSize(88, 21);
		dateChooserCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bindingSupport.bindDateTimeChooserComboWidget(customizerModel, "idCardDate", dateChooserCombo, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label idCardCountryLabel = new Label(composite, SWT.NONE);
		idCardCountryLabel.setText("Pays:       ");
		idCardCountryLabel.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false));
		final Text idCardCountryText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "idCardCountry", idCardCountryText);
		idCardCountryText.setLayoutData(gridData);

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
