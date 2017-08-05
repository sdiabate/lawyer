package com.ngosdi.lawyer.app.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ngosdi.lawyer.app.web.model.ClientCustomizerModel;

public class ClientValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return clazz.equals(ClientCustomizerModel.class);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "requiered.client.firstName", "Field first name is required");
	}

}
