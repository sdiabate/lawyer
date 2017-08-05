package com.ngosdi.lawyer.app.authentication;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ngosdi.lawyer.app.views.common.EImage;

public class ConnectionSplashScreen {

	private final Display display;
	private final Shell shell;

	private boolean loading;

	public ConnectionSplashScreen(final Display display) {
		shell = new Shell(display, SWT.NO_REDRAW_RESIZE);
		shell.setText("Connexion");

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

		final int x = clientArea.x + 100;
		final int y = clientArea.y + 180;

		final Label loginLabel = new Label(shell, SWT.NONE);
		loginLabel.setText("Login:");
		loginLabel.setBounds(x, y - 5, 75, 22);
		loginLabel.setForeground(labelForeground);

		final Text loginText = new Text(shell, SWT.BORDER);
		loginText.setBounds(x + 80, y, 180, 22);
		loginText.setForeground(labelForeground);

		final Label passwordLabel = new Label(shell, SWT.NONE);
		passwordLabel.setText("Mot de passe:");
		passwordLabel.setBounds(x, y + 25, 75, 22);
		passwordLabel.setForeground(labelForeground);

		final Text passwordText = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		passwordText.setBounds(x + 80, y + 25, 180, 22);
		passwordText.setForeground(labelForeground);

		final Button validateButton = new Button(shell, SWT.PUSH);
		validateButton.setText("Valider");
		validateButton.setBounds(x + 142, y + 55, 60, 25);

		final Button cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setText("Annuler");
		cancelButton.setBounds(x + 202, y + 55, 60, 25);

		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				System.exit(-1);
			}
		});

		validateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				authenticate();
			}
		});

		loginText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent event) {
				if (event.keyCode == SWT.CR) {
					authenticate();
				}
			}
		});

		passwordText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent event) {
				if (event.keyCode == SWT.CR) {
					authenticate();
				}
			}
		});

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

	private void authenticate() {
		shell.dispose();
		// IWorkbench wb = PlatformUI.getWorkbench();
		// IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		// // get the service
		// ISourceProviderService service = (ISourceProviderService)
		// window.getService(ISourceProviderService.class);
		// // get our source provider by querying by the variable name
		// SessionSourceProvider sessionSourceProvider = (SessionSourceProvider)
		// service.getSourceProvider(SessionSourceProvider.SESSION_STATE);
		// // set the value
		// sessionSourceProvider.setLoggedIn(true);
	}

}
