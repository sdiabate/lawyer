/**
 *
 */
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
@Table(name = "PAYMENT")
public class Payment extends AbstractDocumentProvider {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "CASE_ID", referencedColumnName = "ID")
	private Case caze;

	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_TYPE")
	private PaymentType paymentType;

	@Temporal(TemporalType.DATE)
	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "COMMENT")
	private String comment;

	public Payment() {
		super();
	}

	public Case getLegalCase() {
		return caze;
	}

	public void setLegalCase(final Case caze) {
		this.caze = caze;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(final PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(final Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(final double amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

}
