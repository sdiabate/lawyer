package com.ngosdi.lawyer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ngosdi.lawyer.beans.AbstractEntity;
import com.ngosdi.lawyer.repositories.IBaseRepository;
import com.ngosdi.lawyer.services.IAuthenticationService;
import com.ngosdi.lawyer.services.IServiceDao;

@Component
public class Application implements ApplicationContextAware {

//	private static Application currentApplication;

	private static ApplicationContext context;
//	private static URL scanUrl;
	
	private static ApplicationArguments args;

//	public Application(final URL scanUrl, final String... profiles) {
//		this.scanUrl = scanUrl;
//		context = new AnnotationConfigApplicationContext();
//		context.getEnvironment().setActiveProfiles(profiles);
//		context.scan("com.ngosdi.lawyer");
//		currentApplication = this;
//	}
//
//	public void init() {
//		context.refresh();
//	}
//
//	public void close() {
//		context.close();
//	}

//	public static Application getCurrentApplication() {
//		return currentApplication;
//	}
//
	public static URL getScanUrl() {
		try {
			return new URL(args.getOptionValues("-scanUrl").get(0));
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
//	public static void setScanUrl(URL scanUrl) {
//		Application.scanUrl = scanUrl;
//	}

	@SuppressWarnings("rawtypes")
	public static <T extends AbstractEntity> Optional<IBaseRepository> getRepository(final Class<T> clazz) {
		for (final Entry<String, IBaseRepository> entry : context.getBeansOfType(IBaseRepository.class).entrySet()) {
			for (final Class<?> interfaze : entry.getValue().getClass().getInterfaces()) {
				for (final Type type : interfaze.getGenericInterfaces()) {
					if (type instanceof ParameterizedType) {
						final Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
						if (genericTypes.length == 2 && genericTypes[0].equals(clazz) && genericTypes[1].equals(Long.class)) {
							return Optional.of(entry.getValue());
						}
					}
				}
			}
		}
		return Optional.empty();
	}

	public static IServiceDao getServiceDao() {
		return context.getBean(IServiceDao.class);
	}

	public static IAuthenticationService getAuthenticationService() {
		return context.getBean(IAuthenticationService.class);
	}
	
	@Autowired
	public void setArgs(ApplicationArguments args) {
		Application.args = args;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
