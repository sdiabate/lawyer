/**
 *
 */
package com.ngosdi.lawyer.app.views.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.views.common.EImage;
import com.ngosdi.lawyer.app.views.common.action.ContextualAction;
import com.ngosdi.lawyer.app.views.common.action.ContextualActionPathElement;
import com.ngosdi.lawyer.app.views.common.action.DataListActionEvent;
import com.ngosdi.lawyer.app.views.common.action.IAction;
import com.ngosdi.lawyer.app.views.common.customizer.AbstractCustomizer;
import com.ngosdi.lawyer.app.views.common.customizer.CustomizerDialog;
import com.ngosdi.lawyer.app.views.common.customizer.DialogBox;
import com.ngosdi.lawyer.app.views.common.customizer.DocumentList;
import com.ngosdi.lawyer.app.views.common.datalist.ColumnDescriptor;
import com.ngosdi.lawyer.app.views.common.datalist.DataListActionModel;
import com.ngosdi.lawyer.app.views.common.datalist.DataListModel;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Payment;
import com.ngosdi.lawyer.services.IServiceDao;

/**
 * @author john
 */
public class PaymentDataListModel extends DataListModel {

	public PaymentDataListModel(final WritableList dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors,
			final DataListActionModel actionModel) {
		super(dataList, labelProvider, columnDescriptors, actionModel);
	}

	public PaymentDataListModel(final WritableList dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors,
			final DataListActionModel actionModel, final String[] xPathExpressions) {
		super(dataList, labelProvider, columnDescriptors, actionModel, xPathExpressions);

	}

	public PaymentDataListModel(final Composite parent) {
		inititalize(parent);
	}

	private void inititalize(final Composite parent) {

		labelProvider = new DataListLabelProvider();

		columnDescriptors = new ColumnDescriptor[8];
		columnDescriptors[0] = new ColumnDescriptor("Id", 0.05, 10);
		columnDescriptors[1] = new ColumnDescriptor("Affaire N°", 0.05, 20);
		columnDescriptors[2] = new ColumnDescriptor("Date", 0.10, 10);
		columnDescriptors[3] = new ColumnDescriptor("Type paiement", 0.10, 50);
		columnDescriptors[4] = new ColumnDescriptor("Montant", 0.20, 50);
		columnDescriptors[5] = new ColumnDescriptor("Total règlement", 0.20, 50);
		columnDescriptors[6] = new ColumnDescriptor("Solde", 0.30, 150);
		columnDescriptors[7] = new ColumnDescriptor("Commentaire", 0.30, 150);

		final IAction createAction = (event) -> {
			final Payment payment = new Payment();
			payment.setPaymentDate(new Date());
			final AbstractCustomizer<Payment> customizer = new PaymentCustomizer(payment);
			final DocumentList documentList = new DocumentList(payment.getDocuments());

			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Dialog.OK) {
				// Save to DB
				final Payment savedPyments = BundleUtil.getService(IServiceDao.class).save(payment);
				((DataListActionEvent) event).getDataListModel().getDataList().add(savedPyments);
				return savedPyments;
			}
			return null;
		};

		final IAction editAction = (event) -> {
			final Payment payment = (Payment) event.getTarget();
			final Case legalCase = payment.getLegalCase();
			double totalPayment = getTotalPayment(legalCase);
			final AbstractCustomizer<Payment> customizer = new PaymentCustomizer(payment, totalPayment);
			final DocumentList documentList = new DocumentList(payment.getDocuments());

			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);

			if (dialog.open() == Dialog.OK) {
				// Merge to DB
				BundleUtil.getService(IServiceDao.class).save(payment);
			}
			return payment;
		};

		final IAction deleteAction = (event) -> {
			final Payment payment = (Payment) event.getTarget();
			// Delete from DB
			BundleUtil.getService(IServiceDao.class).delete(payment);
			((DataListActionEvent) event).getDataListModel().getDataList().remove(payment);
			return payment;
		};

		final IAction newPaymentAction = (event) -> {
			return null;
		};

		final IAction latestPaymentsAction = (event) -> {
			return null;
		};

		final ContextualActionPathElement[] newPaymentPaths = new ContextualActionPathElement[] { new ContextualActionPathElement("Paiements", null),
				new ContextualActionPathElement("Nouveau paiement", EImage.CREATE.getSwtImage()) };
		final ContextualActionPathElement[] latestPaymentPaths = new ContextualActionPathElement[] { new ContextualActionPathElement("Paiements", null),
				new ContextualActionPathElement("Paiements recents", null) };

		final ContextualAction newPaymentContextualAction = new ContextualAction(newPaymentAction, newPaymentPaths);
		final ContextualAction latestCasesContextualAction = new ContextualAction(latestPaymentsAction, latestPaymentPaths);
		final ContextualAction[] contextualActions = new ContextualAction[] { newPaymentContextualAction, latestCasesContextualAction };

		actionModel = new DataListActionModel(createAction, editAction, deleteAction, contextualActions);

		dataList = new WritableList(new ArrayList<Payment>(BundleUtil.getService(IServiceDao.class).getAll(Payment.class)), Payment.class);

	}

	private double getTotalPayment(Case caze) {
		List<Payment> listPayments = BundleUtil.getService(IServiceDao.class).getAll(Payment.class);
		double total = 0.0;
		for (Payment payment : listPayments) {
			if (payment.getLegalCase().getId() == caze.getId()) {
				total += payment.getAmount();
			}
		}
		return total;
	}

	private class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Payment payment = (Payment) object;
			final Case legalCase = payment.getLegalCase();
			switch (column) {
				case 0:
					return String.valueOf(payment.getId());
				case 1:
					return legalCase.getCaseId();
				case 2:
					return payment.getPaymentDate().toString();// TODO : to will
																// be formated
				case 3:
					return payment.getPaymentType().getName();
				case 4:
					return Double.toString(payment.getAmount());
				case 5:
					return String.valueOf(getTotalPayment(legalCase));
				case 6:
					return String.valueOf(legalCase.getHonorary() - getTotalPayment(legalCase));
				case 7:
					return payment.getComment();
				default:
					return "";
			}
		}
	}
}
