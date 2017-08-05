package com.ngosdi.lawyer.app.views.client;

import java.util.Date;

import com.ngosdi.lawyer.app.views.common.EIdCardType;
import com.ngosdi.lawyer.app.views.common.EPhoneType;
import com.ngosdi.lawyer.app.views.common.customizer.AbstractCustomizerModel;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.ClientType;
import com.ngosdi.lawyer.beans.Gender;
import com.ngosdi.lawyer.beans.IdentityCard;

public class ClientCustomizerModel extends AbstractCustomizerModel<Client> {

	private String firstName;
	private String lastName;
	private Gender gender;
	private ClientType clientType;
	private Date birthdate;
	private String country;
	private String city;
	private String post;
	private String landPhone;
	private String mobilePhone;
	private String fax;
	private String address;
	private EIdCardType idCardType;
	private String idCardNumber;
	private Date idCardDate;
	private String idCardCountry;

	protected ClientCustomizerModel() {
		super(null);
	}

	public ClientCustomizerModel(final Client target) {
		super(target);
	}

	@Override
	public void synchronize() {
		firstName = target.getFirstName();
		lastName = target.getLastName();
		gender = target.getGender();
		clientType = target.getClientType();
		birthdate = target.getBirthdate();
		country = target.getCountry();
		city = target.getCity();
		post = target.getPost();
		address = target.getAddress();
		fax = target.getFax();
		mobilePhone = target.getPhones().get(EPhoneType.MOBILE.name());
		landPhone = target.getPhones().get(EPhoneType.LAND.name());
		final IdentityCard idcCard = target.getIdentityCard();
		if (idcCard != null) {
			idCardNumber = idcCard.getNumber();
			if (idcCard.getType() != null) {
				idCardType = EIdCardType.valueOf(idcCard.getType());
			}
			idCardCountry = idcCard.getCountry();
			idCardDate = idcCard.getDeliveryDate();
		}
	}

	@Override
	public void validate() {
		target.setFirstName(firstName);
		target.setLastName(lastName);
		target.setGender(gender);
		target.setClientType(clientType);
		target.setBirthdate(birthdate);
		target.setCountry(country);
		target.setCity(city);
		target.setFax(fax);
		target.setAddress(address);
		target.setPost(post);
		if (mobilePhone != null && !mobilePhone.isEmpty()) {
			target.getPhones().put(EPhoneType.MOBILE.name(), mobilePhone);
		}
		if (landPhone != null && !landPhone.isEmpty()) {
			target.getPhones().put(EPhoneType.LAND.name(), landPhone);
		}
		if (idCardNumber != null && !idCardNumber.isEmpty()) {
			final IdentityCard idcCard = new IdentityCard();
			idcCard.setNumber(idCardNumber);
			if (idCardType != null) {
				idcCard.setType(idCardType.name());
			}
			idcCard.setCountry(idCardCountry);
			idcCard.setDeliveryDate(idCardDate);
			target.setIdentityCard(idcCard);
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(final Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getLandPhone() {
		return landPhone;
	}

	public void setLandPhone(final String landPhone) {
		this.landPhone = landPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(final String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(final String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(final Gender gender) {
		this.gender = gender;
	}

	public ClientType getClientType() {
		return clientType;
	}
	
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}
	
	public String getCountry() {
		return country;
	}

	public String getPost() {
		return post;
	}

	public void setPost(final String post) {
		this.post = post;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public EIdCardType getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(final EIdCardType idCardType) {
		this.idCardType = idCardType;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(final String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public Date getIdCardDate() {
		return idCardDate;
	}

	public void setIdCardDate(final Date idCardDate) {
		this.idCardDate = idCardDate;
	}

	public String getIdCardCountry() {
		return idCardCountry;
	}

	public void setIdCardCountry(final String idCardCountry) {
		this.idCardCountry = idCardCountry;
	}

}
