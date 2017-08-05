/**
 * 
 */
package com.ngosdi.lawyer.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NATURE")
public class Nature extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "NATURE_NAME")
	private String natureName;

	public Nature() {
		super();
	}

	public Nature(String natureName) {
		super();
		this.natureName = natureName;
	}

	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}

}
