package model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;


public class Employee implements Serializable {
	private String code;
	private String lastName;
	private String firstName;
	private String lastKanaName;
	private String firstKanaName;
	private int genderNumber;
	private String gender;
	private Date birthDay;
	private Section section;
	private Date hireDate;
	private Timestamp update;
	private int deleteFlag;

	public Employee() {}

	public Employee(String employeeCode, String lastName, String firstName, String lastKanaName, String firstKanaName,
			int genderNumber, Date birthDay, Section section, Date hireDate) {
		this.code = employeeCode;
		this.lastName = lastName;
		this.firstName = firstName;
		this.lastKanaName = lastKanaName;
		this.firstKanaName = firstKanaName;
		this.genderNumber = genderNumber;
		this.birthDay = birthDay;
		this.section = section;
		this.hireDate = hireDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastKanaName() {
		return lastKanaName;
	}

	public void setLastKanaName(String lastKanaName) {
		this.lastKanaName = lastKanaName;
	}

	public String getFirstKanaName() {
		return firstKanaName;
	}

	public void setFirstKanaName(String firstKanaName) {
		this.firstKanaName = firstKanaName;
	}

	public int getGenderNumber() {
		return this.genderNumber;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(int genderNumber) {
		if (genderNumber == 1) {
			this.genderNumber = 1;
			this.gender = "男";
		} else if (genderNumber == 2) {
			this.genderNumber = 2;
			this.gender = "女";
		} else {
			this.genderNumber = 0;
			this.gender = "不明";
		}
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Timestamp getUpdate() {
		return update;
	}

	public void setUpdate(Timestamp update) {
		this.update = update;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean b) {
		int flg;
		if (b) {
			flg = 1;
		} else {
			flg = 0;
		}
		this.deleteFlag = flg;
	}
}
