package com.ngosdi.lawyer.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CASE")
public class Case extends AbstractDocumentProvider {

	private static final long serialVersionUID = 1L;

	@Column(name = "CASE_ID")
	private String caseId;

	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "OPPOSITE_PART_ID", referencedColumnName = "ID")
	private Client opposite;

	@ManyToOne
	@JoinColumn(name = "CLIENT_NATURE_ID", referencedColumnName = "ID")
	private Nature clientNature;

	@ManyToOne
	@JoinColumn(name = "OPPOSITE_NATURE_ID", referencedColumnName = "ID")
	private Nature oppositeNature;

	@ManyToOne
	@JoinColumn(name = "OPPOSITE_PART_LAWYER_ID", referencedColumnName = "ID")
	private Professional oppositePartLawyer;

	@ManyToOne
	@JoinColumn(name = "SECTION_ID", referencedColumnName = "ID")
	private Section section;

	@ManyToOne
	@JoinColumn(name = "COURT_ID", referencedColumnName = "ID")
	private Court court;

	@Temporal(TemporalType.DATE)
	@Column(name = "DEPOSIT_DATE")
	private Date depositDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "JUDGMENT_DATE")
	private Date judgmentDate;

	@Column(name = "ARCHIVED")
	private boolean archived;

	@Column(name = "ARCHIVE_ID")
	private String archiveId;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "HONORARY")
	private double honorary;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(final String caseId) {
		this.caseId = caseId;
	}

	public Client getClient() {
		return client;
	}

	public Nature getClientNature() {
		return clientNature;
	}

	public void setClientNature(final Nature clientNature) {
		this.clientNature = clientNature;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public Client getOpposite() {
		return opposite;
	}

	public void setOpposite(final Client opposite) {
		this.opposite = opposite;
	}

	public Nature getOppositeNature() {
		return oppositeNature;
	}

	public void setOppositeNature(final Nature oppositeNature) {
		this.oppositeNature = oppositeNature;
	}

	public Professional getOppositePartLawyer() {
		return oppositePartLawyer;
	}

	public void setOppositePartLawyer(final Professional oppositePartLawyer) {
		this.oppositePartLawyer = oppositePartLawyer;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(final Section section) {
		this.section = section;
	}

	public Court getCourt() {
		return court;
	}

	public void setCourt(final Court court) {
		this.court = court;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(final Date depositDate) {
		this.depositDate = depositDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public double getHonorary() {
		return honorary;
	}

	public void setHonorary(double honorary) {
		this.honorary = honorary;
	}

}
