package com.ngosdi.lawyer.app.views.court;

import java.util.ArrayList;

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
import com.ngosdi.lawyer.app.views.common.datalist.ColumnDescriptor;
import com.ngosdi.lawyer.app.views.common.datalist.DataListActionModel;
import com.ngosdi.lawyer.app.views.common.datalist.DataListModel;
import com.ngosdi.lawyer.beans.Court;
import com.ngosdi.lawyer.services.IServiceDao;

public class CourtDataListModel extends DataListModel {

	public CourtDataListModel(final WritableList dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors, final DataListActionModel actionModel) {
		super(dataList, labelProvider, columnDescriptors, actionModel);
	}

	public CourtDataListModel(final WritableList dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors, final DataListActionModel actionModel,
			final String[] xPathExpressions) {
		super(dataList, labelProvider, columnDescriptors, actionModel, xPathExpressions);

	}

	public CourtDataListModel(final Composite parent) {
		inititalize(parent);
	}

	private void inititalize(final Composite parent) {

		parent.setLayout(new GridLayout(1, false));

		labelProvider = new DataListLabelProvider();

		columnDescriptors = new ColumnDescriptor[4];
		columnDescriptors[0] = new ColumnDescriptor("Nom", 0.30, 125);
		columnDescriptors[1] = new ColumnDescriptor("Type", 0.30, 125);
		columnDescriptors[2] = new ColumnDescriptor("Adresse", 0.10, 125);
		columnDescriptors[3] = new ColumnDescriptor("Ville", 0.30, 125);

		final IAction createAction = event -> {
			final Court court = new Court();
			final CourtCustomizer customizer = new CourtCustomizer(court);
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Dialog.OK) {
				// Save to DB
				final Court savedCourt = BundleUtil.getService(IServiceDao.class).save(court);
				((DataListActionEvent) event).getDataListModel().getDataList().add(savedCourt);
				return savedCourt;
			}
			return null;
		};

		final IAction editAction = event -> {
			final Court court = (Court) event.getTarget();
			final CourtCustomizer customizer = new CourtCustomizer(court);
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Dialog.OK) {
				// Merge to DB
				BundleUtil.getService(IServiceDao.class).save(court);
			}
			return court;
		};

		final IAction deleteAction = event -> {
			final Court court = (Court) event.getTarget();
			// Delete from DB
			BundleUtil.getService(IServiceDao.class).delete(court);
			((DataListActionEvent) event).getDataListModel().getDataList().remove(court);
			return court;
		};

		actionModel = new DataListActionModel(createAction, editAction, deleteAction);
		dataList = new WritableList(new ArrayList<Court>(BundleUtil.getService(IServiceDao.class).getAll(Court.class)), Court.class);
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Court court = (Court) object;
			switch (column) {
				case 0:
					return court.getName();
				case 1:
					return court.getType().name();
				case 2:
					return court.getAddress();
				case 3:
					return court.getCity();
			}
			return null;
		}

	}
}
