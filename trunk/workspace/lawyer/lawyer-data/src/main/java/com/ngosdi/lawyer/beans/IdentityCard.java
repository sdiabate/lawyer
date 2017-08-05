package com.ngosdi.lawyer.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class IdentityCard implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_CARD_TYPE")
	private String type;

	@Column(name = "ID_CARD_NUMBER")
	private String number;

	@Temporal(TemporalType.DATE)
	@Column(name = "ID_CARD_DELIVERY_DATE")
	private Date deliveryDate;

	@Column(name = "ID_CARD_DELIVERY_COUNTRY")
	private String country;

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(final Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

}
