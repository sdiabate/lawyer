package com.ngosdi.lawyer.app.views.legalcase;

import java.util.Date;

import com.ngosdi.lawyer.app.views.common.customizer.AbstractCustomizerModel;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.Court;
import com.ngosdi.lawyer.beans.Nature;
import com.ngosdi.lawyer.beans.Professional;
import com.ngosdi.lawyer.beans.Section;

public class CaseCustomizerModel extends AbstractCustomizerModel<Case> {

	private Client client;
	private String clientName;
	private Client adversary;
	private Nature clientNature;
	private Nature oppositeNature;
	private Professional oppositePartLawyer;
	private Section section;
	private Court court;
	private Date depositDate;
	private Date startDate;
	private String subject;
	private String comment;
	private String caseId;
	private String honorary;

	protected CaseCustomizerModel() {
		super(null);
	}

	public CaseCustomizerModel(final Case target) {
		super(target);
	}

	@Override
	public void synchronize() {
		caseId = target.getCaseId();
		client = target.getClient();
		// clientName = client.getFirstName() + ", " + client.getLastName();
		adversary = target.getOpposite();
		clientNature = target.getClientNature();
		oppositeNature = target.getOppositeNature();
		oppositePartLawyer = target.getOppositePartLawyer();
		section = target.getSection();
		court = target.getCourt();
		depositDate = target.getDepositDate();
		startDate = target.getStartDate();
		subject = target.getSubject();
		comment = target.getComment();
		honorary = String.valueOf(target.getHonorary());
	}

	@Override
	public void validate() {
		target.setCaseId(caseId);
		target.setClient(client);
		target.setOpposite(adversary);
		target.setClientNature(clientNature);
		target.setOppositeNature(oppositeNature);
		target.setOppositePartLawyer(oppositePartLawyer);
		target.setSection(section);
		target.setCourt(court);
		target.setStartDate(startDate);
		target.setDepositDate(depositDate);
		target.setSubject(subject);
		target.setComment(comment);
		target.setHonorary(Double.valueOf(honorary));
	}

	public Client getClient() {
		return client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public Client getAdversary() {
		return adversary;
	}

	public void setAdversary(final Client adversary) {
		this.adversary = adversary;
	}

	public Nature getClientNature() {
		return clientNature;
	}

	public void setClientNature(final Nature clientNature) {
		this.clientNature = clientNature;
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

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(final String caseId) {
		this.caseId = caseId;
	}

	public void setClientName(final String clientName) {
		this.clientName = clientName;
	}

	public String getHonorary() {
		return honorary;
	}

	public void setHonorary(String honorary) {
		this.honorary = honorary;
	}

	public String getClientName() {
		return clientName;
	}
}
