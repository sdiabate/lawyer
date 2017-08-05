package com.ngosdi.lawyer.app.views.hearing;

import java.util.Date;

import com.ngosdi.lawyer.app.views.common.customizer.AbstractCustomizerModel;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Hearing;
import com.ngosdi.lawyer.beans.HearingStatus;
import com.ngosdi.lawyer.beans.Professional;

public class HearingCustomizerModel extends AbstractCustomizerModel<Hearing> {

	private Case legalCase;
	private String courtName;
	private Professional substitute;
	private Date date;
	private HearingStatus status;
	private String statusDetail;
	private String member;
	private String comment;

	protected HearingCustomizerModel() {
		super(null);
	}

	public HearingCustomizerModel(final Hearing target) {
		super(target);
	}

	@Override
	public void synchronize() {
		legalCase = target.getLegalCase();
		courtName = legalCase != null ? legalCase.getCourt().getName() : "";
		substitute = target.getSubstitute();
		date = target.getDate();
		status = target.getStatus();
		statusDetail = target.getStatusDetail();
		member = target.getMember();
		comment = target.getComment();
	}

	@Override
	public void validate() {
		target.setLegalCase(legalCase);
		target.setSubstitute(substitute);
		target.setDate(date);
		target.setStatus(status);
		target.setStatusDetail(statusDetail);
		target.setMember(member);
		target.setComment(comment);
	}

	public Case getCase() {
		return legalCase;
	}

	public void setCase(Case legalCase) {
		this.legalCase = legalCase;
		this.setCourtName(legalCase.getCourt().getName());
	}

	public Professional getSubstitute() {
		return substitute;
	}

	public void setSubstitute(Professional substitute) {
		this.substitute = substitute;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public HearingStatus getStatus() {
		return status;
	}

	public void setStatus(HearingStatus status) {
		this.status = status;
	}

	public String getStatusDetail() {
		return statusDetail;
	}

	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}
}
