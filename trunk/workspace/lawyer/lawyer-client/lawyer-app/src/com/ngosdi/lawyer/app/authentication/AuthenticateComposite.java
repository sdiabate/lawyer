/**
 * ------------------------------------------------------------
 * SVN revision information:
 * ------------------------------------------------------------
 *
 * @version $Revision:: 18838 $
 * @date    $Date:: 2014-01-28 20:30:57#$
 */
package com.ngosdi.lawyer.app.authentication;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ngosdi.lawyer.app.views.common.EImage;

public class AuthenticateComposite extends Composite {

	private Text loginField;

	private Text passwordField;

	public AuthenticateComposite(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridLayout layout = new GridLayout(3, false);
		layout.marginTop = 2;
		layout.marginBottom = 10;
		layout.horizontalSpacing = 10;
		this.setLayout(layout);
		createComposite();
		setEnabled(true);

	}

	@Override
	public void dispose() {
		super.dispose();

	}

	private void createComposite() {
		Label imageComposite = new Label(this, SWT.NONE);
		imageComposite.setImage(EImage.SPLASH.getSwtImage());
		imageComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 6, 1));

		// Login
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		Label loginLabel = new Label(this, SWT.NONE);
		loginLabel.setText("login");
		loginField = new Text(this, SWT.BORDER);
		loginField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);

		// Password
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		Label passwordLabel = new Label(this, SWT.NONE);
		passwordLabel.setText("password");
		passwordField = new Text(this, SWT.SINGLE | SWT.LEAD | SWT.PASSWORD | SWT.BORDER);
		passwordField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	public Text getLoginField() {
		return loginField;
	}

	public Text getPasswordField() {
		return passwordField;
	}
}
