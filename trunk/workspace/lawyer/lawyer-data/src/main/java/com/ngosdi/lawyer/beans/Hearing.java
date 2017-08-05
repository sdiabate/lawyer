package com.ngosdi.lawyer.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "HEARING")
public class Hearing extends AbstractDocumentProvider {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "CASE_ID", referencedColumnName = "ID")
	private Case legalCase;

	@ManyToOne
	@JoinColumn(name = "SUBSTITUTE_ID", referencedColumnName = "ID")
	private Professional substitute;

	@Temporal(TemporalType.DATE)
	@Column(name = "HEARING_DATE")
	private Date date;

	@Enumerated(EnumType.STRING)
	@Column(name = "HEARING_STATUS")
	private HearingStatus status;

	@Column(name = "STATUS_DETAIL")
	private String statusDetail;

	@Column(name = "STAFF_MEMBER")
	private String member;

	@Column(name = "COMMENT")
	private String comment;

	public Case getLegalCase() {
		return legalCase;
	}

	public void setLegalCase(final Case legalCase) {
		this.legalCase = legalCase;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public HearingStatus getStatus() {
		return status;
	}

	public void setStatus(final HearingStatus status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public Professional getSubstitute() {
		return substitute;
	}

	public void setSubstitute(final Professional substitute) {
		this.substitute = substitute;
	}

	public String getMember() {
		return member;
	}

	public void setMember(final String member) {
		this.member = member;
	}

	public String getStatusDetail() {
		return statusDetail;
	}

	public void setStatusDetail(final String statusDetail) {
		this.statusDetail = statusDetail;
	}
}
