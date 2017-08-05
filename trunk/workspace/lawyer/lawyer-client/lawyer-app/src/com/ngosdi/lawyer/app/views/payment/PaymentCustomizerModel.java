package com.ngosdi.lawyer.app.views.payment;

import java.util.Date;

import com.ngosdi.lawyer.app.views.common.customizer.AbstractCustomizerModel;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Payment;
import com.ngosdi.lawyer.beans.PaymentType;

public class PaymentCustomizerModel extends AbstractCustomizerModel<Payment> {

	private Case legalCase;
	private PaymentType paymentType;
	private Date paymentDate;
	private Double amount;
	private String comment;
	private Double balance;
	private Double total;

	protected PaymentCustomizerModel() {
		super(null);
	}

	public PaymentCustomizerModel(final Payment target) {
		super(target);
	}

	@Override
	public void synchronize() {
		legalCase = target.getLegalCase();
		paymentType = target.getPaymentType();
		paymentDate = target.getPaymentDate();
		amount = target.getAmount();
		comment = target.getComment();
	}

	@Override
	public void validate() {
		target.setLegalCase(legalCase);
		target.setPaymentType(paymentType);
		target.setPaymentDate(paymentDate);
		target.setAmount(amount);
		target.setComment(comment);
	}

	public Case getCase() {
		return legalCase;
	}

	public void setCase(final Case legalCase) {
		this.legalCase = legalCase;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(final Double balance) {
		this.balance = balance;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double totalPayment) {
		this.total = totalPayment;
		if (legalCase != null) {
			setBalance(legalCase.getHonorary() - totalPayment);
		}
	}

}
