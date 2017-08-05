package com.ngosdi.lawyer.app;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.UIEvents.UILifeCycle;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.equinox.security.auth.ILoginContext;
import org.eclipse.equinox.security.auth.LoginContextFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.ngosdi.lawyer.Application;
import com.ngosdi.lawyer.app.views.common.EImage;
import com.ngosdi.lawyer.services.IAuthenticationService;
import com.ngosdi.lawyer.services.IServiceDao;

public class ApplicationLifeCycleManager {

	private static final String JAAS_CONFIG_FILE = "jaas_config.txt";

	private static final String JAAS_CONFIG_NAME = "LAWYER";

	@Inject
	private Logger logger;

	@Inject
	@Optional
	private MApplication application;

	@Inject
	private EModelService modelService;

	@Inject
	private EPartService partService;

	@Inject
	private IEventBroker eventBroker;

	@Inject
	private IEclipseContext eclipseContext;

	@Inject
	@Optional
	private Shell shell;

	@PostContextCreate
	void postContextCreate(final IApplicationContext applicationContext, final Display display) {
		applicationContext.applicationRunning();

		Window.setDefaultImage(EImage.COURT.getSwtImage());

		eventBroker.subscribe(UILifeCycle.APP_STARTUP_COMPLETE, new EventHandler() {
			@Override
			public void handleEvent(final Event event) {
				registerPartViewService();
				registerE4Service();
				eventBroker.unsubscribe(this);
				eventBroker.post(IApplicationEvent.E4_SERVICES_AVAILABLE, null);
			}
		});

		new Thread(() -> registerDataService()).start();

		// Authentication using JAAS
		final BundleContext bundleContext = Activator.getContext();
		final URL configUrl = bundleContext.getBundle().getEntry(JAAS_CONFIG_FILE);
		final ILoginContext secureContext = LoginContextFactory.createContext(JAAS_CONFIG_NAME, configUrl);
		int count = 0;
		boolean logged;
		do {
			try {
				count++;
				secureContext.login();
				logged = true;
			} catch (final LoginException e) {
				logged = false;
				count++;
				logger.error(e);
			}
		} while (!logged || count > 3);
	}

	private void registerDataService() {
		final IServiceDao daoService = Application.getServiceDao();
		ContextInjectionFactory.inject(daoService, eclipseContext);
		BundleUtil.registerService(IServiceDao.class, daoService);

		final IAuthenticationService authService = Application.getAuthenticationService();
		ContextInjectionFactory.inject(authService, eclipseContext);
		BundleUtil.registerService(IAuthenticationService.class, authService);
	}

	private void registerPartViewService() {
		final PartViewService partViewService = new PartViewService();
		ContextInjectionFactory.inject(partViewService, eclipseContext);
		BundleUtil.registerService(PartViewService.class, partViewService);
	}

	private void registerE4Service() {
		final E4Service e4Service = new E4Service(application, modelService, partService, eventBroker);
		BundleUtil.registerService(E4Service.class, e4Service);
	}
}
