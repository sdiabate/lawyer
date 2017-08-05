package com.ngosdi.lawyer.app.views.payment;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.formattedtext.DoubleFormatter;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
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
import com.ngosdi.lawyer.beans.Payment;
import com.ngosdi.lawyer.beans.PaymentType;

public class PaymentCustomizer extends AbstractCustomizer<Payment> {

	private final PaymentCustomizerModel customizerModel;

	public PaymentCustomizer(final Payment payment, final double totalPayment) {
		super(payment, "Nouvel règlement", "Cette fenêtre permet de créer un nouvel règlement");
		PaymentCustomizerModel target = new PaymentCustomizerModel(payment);
		target.setTotal(totalPayment);
		customizerModel = ProxyFactory.createProxy(target);
	}

	public PaymentCustomizer(final Payment payment) {
		this(payment, 0.0);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createArea(final Composite parent, final int style) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(4, false);
		composite.setLayout(gridLayout);

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

		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));

		new TitledSeparator(composite, "Détail paiement").setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));

		final Label paymentTypeLabel = new Label(composite, SWT.NONE);
		paymentTypeLabel.setText("Type paiement: ");

		final ComboViewer paymentTypeComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		final Combo paymentTypeCombo = paymentTypeComboViewer.getCombo();
		paymentTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		paymentTypeComboViewer.setContentProvider(ArrayContentProvider.getInstance());

		paymentTypeComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((PaymentType) element).getName();
			}
		});
		paymentTypeComboViewer.setInput(PaymentType.values());
		bindingSupport.bindComboViewer(customizerModel, "paymentType", paymentTypeComboViewer, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label dateLabel = new Label(composite, SWT.NONE);
		dateLabel.setText("Date:");

		final DateTime paymentDate = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		paymentDate.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		paymentDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bindingSupport.bindDateTimeChooserComboWidget(customizerModel, "paymentDate", paymentDate, IValidationSupport.NOT_EMPTY_VALIDATOR);

		new TitledSeparator(composite, "Montant versé").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 4, 1));

		final Label totalLabel = new Label(composite, SWT.NONE);
		totalLabel.setText("Total: ");
		final FormattedText totalText = new FormattedText(composite, SWT.BORDER);
		totalText.getControl().setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false));
		totalText.getControl().setEnabled(false);
		bindingSupport.bindFormattedText(customizerModel, "total", totalText, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label amountLabel = new Label(composite, SWT.NONE);
		amountLabel.setText("Montant:");

		final FormattedText paymentAmount = new FormattedText(composite, SWT.BORDER);
		paymentAmount.setFormatter(new DoubleFormatter("########0.00"));
		paymentAmount.getControl().setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		bindingSupport.bindFormattedText(customizerModel, "amount", paymentAmount, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label balanceLabel = new Label(composite, SWT.NONE);
		balanceLabel.setText("Solde:");

		final FormattedText balanceText = new FormattedText(composite, SWT.BORDER);
		balanceText.getControl().setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		balanceText.getControl().setEnabled(false);
		bindingSupport.bindFormattedText(customizerModel, "balance", balanceText);

		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));

		final Label commentLabel = new Label(composite, SWT.NONE);
		commentLabel.setText("Commentaire:");

		final Text commentText = new Text(composite, SWT.BORDER);
		commentText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 4, 1));
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
