package com.ngosdi.lawyer.app.views.court;

import com.ngosdi.lawyer.app.views.common.customizer.AbstractCustomizerModel;
import com.ngosdi.lawyer.beans.Court;
import com.ngosdi.lawyer.beans.CourtType;

public class CourtCustomizerModel extends AbstractCustomizerModel<Court> {

	private String name;

	private CourtType type;

	private String city;

	private String address;

	private String fax;

	private String tel;

	protected CourtCustomizerModel() {
		super(null);
	}

	public CourtCustomizerModel(final Court target) {
		super(target);
	}

	@Override
	public void synchronize() {
		name = target.getName();
		type = target.getType();
		city = target.getCity();
		address = target.getAddress();
		fax = target.getFax();
		tel = target.getPhones().get("tel");
		fax = target.getPhones().get("fax");
	}

	@Override
	public void validate() {
		// target.setClient(client);
		target.setName(name);
		target.setType(type);
		target.setCity(city);
		target.setAddress(address);
		target.setFax(fax);
		target.getPhones().clear();
		target.getPhones().put("tel", tel);
		target.getPhones().put("fax", fax);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CourtType getType() {
		return type;
	}

	public void setType(CourtType type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
