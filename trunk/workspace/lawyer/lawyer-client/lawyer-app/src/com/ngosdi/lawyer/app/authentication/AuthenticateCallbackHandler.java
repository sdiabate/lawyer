package com.ngosdi.lawyer.app.authentication;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ngosdi.lawyer.app.views.common.EImage;

public class AuthenticateCallbackHandler extends Dialog implements CallbackHandler {

	/** The process callbacks. */
	public boolean processCallbacks;
	/** The is quit. */
	public boolean isQuit;
	/** The callback array. */
	public Callback[] callbackArray;

	/** Empty Array Callbacks */
	private static final Callback[] EMPTY_ARRAY_CALLBACKS = new Callback[0];

	/** Connect Button , related to the OK Windows Builder button. */
	private static final String CONNECT_BUTTON = "connect";

	/** Quit Button , related to the Cancel Windows Builder button. */
	private static final String QUIT_BUTTON = "quit";

	/** Welcome title in the dialo. */
	private static final String WELCOME_TITLE = "Lawyer user authentication";

	private AuthenticateComposite authentificateComposite;

	public AuthenticateCallbackHandler() {
		super(new Shell(Display.getDefault()));
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		authentificateComposite = new AuthenticateComposite(parent, SWT.NONE);
		GridData layoutData = new GridData();
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		authentificateComposite.setLayoutData(layoutData);

		try {
			createCallbackHandlers(authentificateComposite);
		}
		catch (UnsupportedCallbackException e) {
			e.printStackTrace();
		}

		return authentificateComposite;
	}

	private void createCallbackHandlers(AuthenticateComposite composite) throws UnsupportedCallbackException {
		Callback[] callbacks = getCallbacks();
		for (Callback callback : callbacks) {
			if (callback instanceof NameCallback) {
				createNameHandler(composite, (NameCallback) callback);
			}
			else if (callback instanceof PasswordCallback) {
				createPasswordHandler(composite, (PasswordCallback) callback);
			}
			else {
				throw new UnsupportedCallbackException(callback, "Unrecognized Callback");
			}
		}
	}

	private void createPasswordHandler(AuthenticateComposite composite, final PasswordCallback callback) {

		final Text passwordField = composite.getPasswordField();
		final Text loginField = composite.getLoginField();
		passwordField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				callback.setPassword(passwordField.getText().toCharArray());
				if ((loginField.getText().length() > 0) && (passwordField.getText().length() > 0)) {
					getButton(IDialogConstants.OK_ID).setEnabled(true);
				}
				else {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
				}
			}
		});
	}

	private void createNameHandler(AuthenticateComposite composite, final NameCallback callback) {

		final Text passwordField = composite.getPasswordField();
		final Text loginField = composite.getLoginField();
		loginField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				callback.setName(loginField.getText());
				if (loginField.getText().length() > 0) {
					if (passwordField.getText().length() > 0) {
						getButton(IDialogConstants.OK_ID).setEnabled(true);
					}
					else {
						getButton(IDialogConstants.OK_ID).setEnabled(false);
					}
				}
				else {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
				}
			}
		});
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button okButton = createButton(parent, IDialogConstants.OK_ID, CONNECT_BUTTON, true);
		GridLayout gridLayout = (GridLayout) parent.getLayout();
		gridLayout.numColumns++;
		okButton.setEnabled(false);
		Label filler = new Label(parent, SWT.NONE);
		filler.setLayoutData(GridDataFactory.fillDefaults().grab(false, false).create());
		gridLayout.numColumns++;
		createButton(parent, IDialogConstants.CANCEL_ID, QUIT_BUTTON, false);
		gridLayout.numColumns++;
	}

	@Override
	protected void setButtonLayoutData(Button button) {
		GridData data = GridDataFactory.fillDefaults().grab(false, false).span(2, 1).create();
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		Point minSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minSize.x);
		button.setLayoutData(data);
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Composite bar = new Composite(parent, SWT.NONE);
		parent.setBackground(bar.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayout layout = GridLayoutFactory.swtDefaults().equalWidth(true).numColumns(1).create();
		bar.setLayout(layout);
		bar.setLayoutData(GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false).create());
		Label filler = new Label(bar, SWT.NONE);
		filler.setLayoutData(GridDataFactory.fillDefaults().grab(false, false).span(1, 1).create());
		createButtonsForButtonBar(bar);
		layout.numColumns++;
		filler = new Label(bar, SWT.NONE);
		filler.setLayoutData(GridDataFactory.fillDefaults().grab(false, false).span(6, 1).create());
		return bar;
	}

	@Override
	protected Point getInitialLocation(Point initialSize) {
		Composite shell = getShell();
		Monitor monitor = shell.getMonitor();
		Rectangle monitorBounds = monitor.getClientArea();
		Point centerPoint = Geometry.centerPoint(monitorBounds);

		return new Point(centerPoint.x - (initialSize.x / 2), Math.max(monitorBounds.y,
				Math.min(centerPoint.y - (initialSize.y * 2 / 3), monitorBounds.y + monitorBounds.height - initialSize.y)));
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(WELCOME_TITLE);
		newShell.setImage(EImage.SPLASH.getSwtImage());
	}

	@Override
	protected void cancelPressed() {
		System.exit(0);
	}

	@Override
	public boolean close() {
		authentificateComposite.dispose();
		return super.close();
	}

	protected final Callback[] getCallbacks() {
		return this.callbackArray;
	}

	public boolean isQuit() {
		return isQuit;
	}

	public void setConnectButton() {
		final Button connectButton = getButton(IDialogConstants.OK_ID);
		connectButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				processCallbacks = true;
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent event) {
				// nothing to do
			}
		});
	}

	public void setQuitButton() {
		final Button quitButton = getButton(IDialogConstants.CANCEL_ID);
		quitButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				isQuit = true;
				processCallbacks = true;
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent event) {
				// nothing to do
			}
		});
	}

	public void setListenerClose(Display display) {
		display.getActiveShell().addListener(SWT.Close, new Listener() {

			@Override
			public void handleEvent(Event event) {

				System.exit(0);
			}
		});
	}

	@Override
	public void handle(Callback[] newCallbacks) throws IOException, UnsupportedCallbackException {
		if (newCallbacks == null) {
			this.callbackArray = EMPTY_ARRAY_CALLBACKS;
		}
		else {
			this.callbackArray = Arrays.copyOf(newCallbacks, newCallbacks.length);
		}
		final Display display = Display.getDefault();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				isQuit = false;
				setBlockOnOpen(false);
				open();
				setConnectButton();
				setQuitButton();
				setListenerClose(display);
			}
		});

		ModalContext.setAllowReadAndDispatch(true); // Works for now.
		try {
			ModalContext.run(new IRunnableWithProgress() {
				@Override
				public void run(final IProgressMonitor monitor) {
					// Wait here until OK or cancel is pressed, then let it rip.
					// The event
					// listener
					// is responsible for closing the dialog (in the
					// loginSucceeded
					// event).
					while (!processCallbacks) {
						try {
							Thread.sleep(100);
						}
						catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					processCallbacks = false;

				}
			}, true, new NullProgressMonitor(), Display.getDefault());
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
