/**
 *
 */
package com.ngosdi.lawyer.app.views.hearing;

import java.util.ArrayList;

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
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.Hearing;
import com.ngosdi.lawyer.services.IServiceDao;

/**
 * @author user
 */
public class HearingDataListModel extends DataListModel {

	public HearingDataListModel(final WritableList dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors, final DataListActionModel actionModel) {
		super(dataList, labelProvider, columnDescriptors, actionModel);
	}

	public HearingDataListModel(final WritableList dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors, final DataListActionModel actionModel,
			final String[] xPathExpressions) {
		super(dataList, labelProvider, columnDescriptors, actionModel, xPathExpressions);

	}

	public HearingDataListModel(final Composite parent) {
		inititalize(parent);
	}

	private void inititalize(final Composite parent) {

		labelProvider = new DataListLabelProvider();

		columnDescriptors = new ColumnDescriptor[7];
		columnDescriptors[0] = new ColumnDescriptor("Id", 0.05, 10);
		columnDescriptors[1] = new ColumnDescriptor("Affaire N°", 0.05, 20);
		columnDescriptors[2] = new ColumnDescriptor("Juridiction", 0.20, 100);
		columnDescriptors[3] = new ColumnDescriptor("Date", 0.10, 10);
		columnDescriptors[4] = new ColumnDescriptor("Statut", 0.10, 50);
		columnDescriptors[5] = new ColumnDescriptor("Pour", 0.20, 100);
		columnDescriptors[6] = new ColumnDescriptor("Commentaire", 0.30, 150);

		final IAction createAction = (event) -> {
			final Hearing hearing = new Hearing();

			final AbstractCustomizer<Hearing> customizer = new HearingCustomizer(hearing);
			final DocumentList documentList = new DocumentList(hearing.getDocuments());

			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Dialog.OK) {
				// Save to DB
				final Hearing savedHearing = BundleUtil.getService(IServiceDao.class).save(hearing);
				((DataListActionEvent) event).getDataListModel().getDataList().add(savedHearing);
				return savedHearing;
			}
			return null;
		};

		final IAction editAction = (event) -> {
			final Hearing hearing = (Hearing) event.getTarget();

			final AbstractCustomizer<Hearing> customizer = new HearingCustomizer(hearing);
			final DocumentList documentList = new DocumentList(hearing.getDocuments());

			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);

			if (dialog.open() == Dialog.OK) {
				// Merge to DB
				BundleUtil.getService(IServiceDao.class).save(hearing);
			}
			return hearing;
		};

		final IAction deleteAction = (event) -> {
			final Hearing hearing = (Hearing) event.getTarget();
			// Delete from DB
			BundleUtil.getService(IServiceDao.class).delete(hearing);
			((DataListActionEvent) event).getDataListModel().getDataList().remove(hearing);
			return hearing;
		};

		final IAction newHearingAction = (event) -> {
			return null;
		};

		final IAction latestHearingsAction = (event) -> {
			return null;
		};

		final ContextualActionPathElement[] newHearingPaths = new ContextualActionPathElement[] { new ContextualActionPathElement("Audiences", null),
				new ContextualActionPathElement("Nouvelle audience", EImage.CREATE.getSwtImage()) };
		final ContextualActionPathElement[] latestHearingPaths = new ContextualActionPathElement[] { new ContextualActionPathElement("Audiences", null),
				new ContextualActionPathElement("Audiences recentes", null) };

		final ContextualAction newHearingContextualAction = new ContextualAction(newHearingAction, newHearingPaths);
		final ContextualAction latestCasesContextualAction = new ContextualAction(latestHearingsAction, latestHearingPaths);
		final ContextualAction[] contextualActions = new ContextualAction[] { newHearingContextualAction, latestCasesContextualAction };

		actionModel = new DataListActionModel(createAction, editAction, deleteAction, contextualActions);

		dataList = new WritableList(new ArrayList<Hearing>(BundleUtil.getService(IServiceDao.class).getAll(Hearing.class)), Hearing.class);

	}

	private class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Hearing hearing = (Hearing) object;
			switch (column) {
				case 0:
					return String.valueOf(hearing.getId());
				case 1:
					return hearing.getLegalCase().getCaseId();
				case 2:
					return hearing.getLegalCase().getCourt().getName();
				case 3:
					return hearing.getDate().toString();
				case 4:
					return hearing.getStatus().getName();
				case 5:
					final Client client = hearing.getLegalCase().getClient();
					return client.getFirstName() + " " + client.getLastName();
				case 6:
					return hearing.getComment();

				default:
					return null;
			}
		}
	}
}
