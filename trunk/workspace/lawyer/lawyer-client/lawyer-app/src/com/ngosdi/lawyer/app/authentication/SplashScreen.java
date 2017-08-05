package com.ngosdi.lawyer.app.authentication;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import com.ngosdi.lawyer.app.views.common.EImage;

public class SplashScreen {

	private final Display display;
	private final Shell shell;

	private boolean loading;

	public SplashScreen(final Display display) {
		shell = new Shell(display, SWT.TOOL | SWT.NO_TRIM);

		loading = true;
		this.display = display;

		shell.setBackgroundImage(EImage.SPLASH.getSwtImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		shell.setBounds(SWT.DEFAULT, SWT.DEFAULT, 452, 302);
		setLocation(shell);
	}

	public void display() {
		final Rectangle clientArea = shell.getClientArea();

		final Color labelForeground = display.getSystemColor(SWT.COLOR_WHITE);
		final Label taskLabel = new Label(shell, SWT.NONE);
		taskLabel.setText("Chargement en cours ...");
		taskLabel.setBounds(clientArea.x + 5, clientArea.y + clientArea.height - 40, clientArea.width - 10, 15);
		taskLabel.setForeground(labelForeground);

		final ProgressBar progressBar = new ProgressBar(shell, SWT.INDETERMINATE);
		progressBar.setBounds(clientArea.x + 5, clientArea.y + clientArea.height - 20, clientArea.width - 10, 5);

		shell.open();

		while (loading && !shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void loadingFinished() {
		loading = false;
	}

	public void dispose() {
		shell.dispose();
	}

	private void setLocation(final Shell shell) {
		final Monitor monitor = display.getPrimaryMonitor();
		final Rectangle monitorRect = monitor.getBounds();
		final Rectangle shellRect = shell.getBounds();
		final int x = monitorRect.x + (monitorRect.width - shellRect.width) / 2;
		final int y = monitorRect.y + (monitorRect.height - shellRect.height) / 2;
		shell.setLocation(x, y);
	}
}
