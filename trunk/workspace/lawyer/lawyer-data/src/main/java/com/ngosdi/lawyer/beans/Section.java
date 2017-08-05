/**
 * 
 */
package com.ngosdi.lawyer.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SECTION")
public class Section extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "SECTION_NAME")
	private String sectionName;

	public Section() {
		super();
	}

	public Section(String sectionName) {
		super();
		this.sectionName = sectionName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

}
