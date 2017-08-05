package com.ngosdi.lawyer.beans;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Document {

	@Column(name = "PATH", nullable = false)
	private String path;

	@Column(name = "DESCRIPTION")
	private String description;

	public Document() {
	}

	public Document(final String path, final String description) {
		this.path = path;
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
