package com.ngosdi.lawyer.app.web.model;

import com.ngosdi.lawyer.beans.AbstractEntity;

public abstract class AbstractCustomizerModel<T extends AbstractEntity> {

	protected Long id;

	public AbstractCustomizerModel(final T target) {
		id = target.getId();
		synchronize(target);
	}

	protected abstract void synchronize(T target);

	protected abstract void validate(T target);

	public Long getId() {
		return id;
	}
}
