package com.ngosdi.lawyer.services;

import java.util.Optional;

import com.ngosdi.lawyer.beans.User;

public interface IAuthenticationService {

	Optional<User> connect(String login, String password);

	User getCurrentUser();
}
