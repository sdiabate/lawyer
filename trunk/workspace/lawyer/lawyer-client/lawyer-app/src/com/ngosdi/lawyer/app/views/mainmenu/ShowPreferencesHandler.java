package com.ngosdi.lawyer.app.views.mainmenu;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.widgets.Shell;

import com.ngosdi.lawyer.app.views.preferences.FilterPreferenceDialog;
import com.ngosdi.lawyer.app.views.preferences.GeneralPreferencePage;
import com.ngosdi.lawyer.app.views.preferences.LawyerPreferenceStore;
import com.ngosdi.lawyer.app.views.preferences.ReportPreferencePage;

public class ShowPreferencesHandler {

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) final Shell shell) {
		final PreferenceManager preferenceManager = new PreferenceManager();
		final PreferenceDialog dialog = new FilterPreferenceDialog(shell, preferenceManager);
		dialog.setPreferenceStore(new LawyerPreferenceStore());
		preferenceManager.addToRoot(new PreferenceNode("lawyer.preference.general", new GeneralPreferencePage()));
		preferenceManager.addToRoot(new PreferenceNode("lawyer.preference.report", new ReportPreferencePage()));
		dialog.create();
		dialog.getTreeViewer().setComparator(new ViewerComparator());
		dialog.getTreeViewer().expandAll();
		dialog.open();
	}
}