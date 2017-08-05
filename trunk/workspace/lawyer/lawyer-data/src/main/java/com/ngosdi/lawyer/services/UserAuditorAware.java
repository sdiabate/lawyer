package com.ngosdi.lawyer.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ngosdi.lawyer.beans.User;

public class UserAuditorAware implements AuditorAware<User> {

	@Override
	public User getCurrentAuditor() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		}

		return (User) authentication.getPrincipal();
	}
}