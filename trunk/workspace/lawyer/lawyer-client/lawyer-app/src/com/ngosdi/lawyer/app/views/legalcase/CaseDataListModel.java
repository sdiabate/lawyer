package com.ngosdi.lawyer.app.views.legalcase;

import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.views.common.action.DataListActionEvent;
import com.ngosdi.lawyer.app.views.common.action.IAction;
import com.ngosdi.lawyer.app.views.common.customizer.CustomizerDialog;
import com.ngosdi.lawyer.app.views.common.customizer.DialogBox;
import com.ngosdi.lawyer.app.views.common.customizer.DocumentList;
import com.ngosdi.lawyer.app.views.common.datalist.ColumnDescriptor;
import com.ngosdi.lawyer.app.views.common.datalist.DataListActionModel;
import com.ngosdi.lawyer.app.views.common.datalist.DataListModel;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.services.IServiceDao;

public class CaseDataListModel extends DataListModel {

	private List<Case> cases;

	public CaseDataListModel(final WritableList dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors, final DataListActionModel actionModel) {
		super(dataList, labelProvider, columnDescriptors, actionModel);
	}

	public CaseDataListModel(final WritableList dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors, final DataListActionModel actionModel,
			final String[] xPathExpressions) {
		super(dataList, labelProvider, columnDescriptors, actionModel, xPathExpressions);
	}

	public CaseDataListModel(final Composite parent) {
		inititalize(parent);
	}

	private void inititalize(final Composite parent) {

		parent.setLayout(new GridLayout(1, false));

		labelProvider = new DataListLabelProvider();

		columnDescriptors = new ColumnDescriptor[4];
		columnDescriptors[0] = new ColumnDescriptor("N° Affaire", 0.30, 125);
		columnDescriptors[1] = new ColumnDescriptor("Pour", 0.30, 125);
		columnDescriptors[2] = new ColumnDescriptor("Contre", 0.10, 125);
		columnDescriptors[3] = new ColumnDescriptor("Juridiction", 0.30, 125);

		final IAction createAction = (event) -> {
			final Case caze = new Case();
			final CaseCustomizer customizer = new CaseCustomizer(caze);
			final DocumentList documentList = new DocumentList(caze.getDocuments());
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Dialog.OK) {
				// Save to DB
				final Case savedCase = BundleUtil.getService(IServiceDao.class).save(caze);
				((DataListActionEvent) event).getDataListModel().getDataList().add(savedCase);
				return savedCase;
			}
			return null;
		};

		final IAction editAction = (event) -> {
			final Case caze = (Case) event.getTarget();
			final CaseCustomizer customizer = new CaseCustomizer(caze);
			final DocumentList documentList = new DocumentList(caze.getDocuments());
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Dialog.OK) {
				// Merge to DB
				BundleUtil.getService(IServiceDao.class).save(caze);
			}
			return caze;
		};

		final IAction deleteAction = (event) -> {
			final Case caze = (Case) event.getTarget();
			// Delete from DB
			BundleUtil.getService(IServiceDao.class).delete(caze);
			((DataListActionEvent) event).getDataListModel().getDataList().remove(caze);
			return caze;
		};
		actionModel = new DataListActionModel(createAction, editAction, deleteAction);

		cases = BundleUtil.getService(IServiceDao.class).getAll(Case.class);
		dataList = new WritableList(cases, Case.class);
	}

	public List<Case> getCases() {
		return cases;
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Case caze = (Case) object;
			switch (column) {
				case 0:
					return caze.getCaseId();
				case 1:
					if (caze.getClient() != null) {
						return caze.getClient().getFirstName() + ", " + caze.getClient().getLastName();
					}

				case 2:
					if (caze.getOpposite() != null) {
						return caze.getOpposite().getFirstName() + ", " + caze.getOpposite().getLastName();
					}

				case 3:
					if (caze.getCourt() != null) {
						return caze.getCourt().getName();
					}

				default:
					return "";
			}
		}
	}
}
