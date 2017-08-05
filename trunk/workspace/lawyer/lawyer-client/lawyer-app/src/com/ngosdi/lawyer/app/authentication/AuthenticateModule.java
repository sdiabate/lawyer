package com.ngosdi.lawyer.app.authentication;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.beans.User;
import com.ngosdi.lawyer.services.IAuthenticationService;
import com.ngosdi.lawyer.services.PasswordUtils;

public class AuthenticateModule implements LoginModule {

	/** LOGIN LABEL. */
	private static final String LOGIN_LABEL = "login";

	/** PASSWORD LABEL. */
	private static final String PASSWORD_LABEL = "password";

	/** The callback handler. */
	private CallbackHandler callbackHandler;

	public AuthenticateModule() {
	}

	@Override
	public boolean abort() throws LoginException {
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		return true;
	}

	@Override
	public boolean login() throws LoginException {
		boolean result = false;
		final User u = loginWithPrompt();

		// do we have a user ?
		if (u != null) {
			result = true;
		}
		return true;
	}

	/**
	 * Prompt login page for user inputs.
	 *
	 * @return true if successful login
	 */
	private User loginWithPrompt() {

		final NameCallback nameCallback = new NameCallback(LOGIN_LABEL + ": ");
		final PasswordCallback passwordCallback = new PasswordCallback(PASSWORD_LABEL + ": ", false);

		try {
			if (callbackHandler == null) {
				return null;
			}
			callbackHandler.handle(new Callback[] { nameCallback, passwordCallback });
		} catch (final IOException e) {
			System.out.println("error in login");
			e.printStackTrace();
		} catch (final UnsupportedCallbackException e) {
			System.out.println("error in login");
			e.printStackTrace();
		}

		final String loginName = nameCallback.getName();
		String password = null;
		if (passwordCallback.getPassword() != null) {
			password = PasswordUtils.hash(passwordCallback.getPassword());
		}

		if (loginName != null && password != null) {
			final Optional<User> result = BundleUtil.getService(IAuthenticationService.class).connect(loginName, password);
			if (result.isPresent()) {
				return result.get();
			}
		}
		return null;
	}

	@Override
	public boolean logout() throws LoginException {
		return true;
	}

	@Override
	public void initialize(final Subject mySubject, final CallbackHandler myCallbackHandler, final Map<String, ?> sharedState, final Map<String, ?> options) {
		callbackHandler = myCallbackHandler;
	}
}
