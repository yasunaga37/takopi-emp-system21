package model.entity;

import java.io.Serializable;
import java.sql.Date;

public class GetLicense implements Serializable {
	private String licenseCode;
	private String licenseName;
	private String employeeLname;
	private String employeeFname;
	private Date getLicenseDate;

	public GetLicense() {}

	public GetLicense(String licenseCode, String licenseName, String employeeLname, String employeeFname,
			Date getLicenseDate) {
		this.licenseCode = licenseCode;
		this.licenseName = licenseName;
		this.employeeLname = employeeLname;
		this.employeeFname = employeeFname;
		this.getLicenseDate = getLicenseDate;
	}

	public String getLicenseCode() {
		return licenseCode;
	}

	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public String getEmployeeLname() {
		return employeeLname;
	}

	public void setEmployeeLname(String employeeLname) {
		this.employeeLname = employeeLname;
	}

	public String getEmployeeFname() {
		return employeeFname;
	}

	public void setEmployeeFname(String employeeFname) {
		this.employeeFname = employeeFname;
	}

	public Date getGetLicenseDate() {
		return getLicenseDate;
	}

	public void setGetLicenseDate(Date getLicenseDate) {
		this.getLicenseDate = getLicenseDate;
	}
}
