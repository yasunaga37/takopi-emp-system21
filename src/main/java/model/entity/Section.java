package model.entity;

import java.io.Serializable;
import java.util.Date;

public class Section implements Serializable {
	private String code;
	private String name;
	private Date update;

	public Section() {}

	public Section(String code, String name, Date update) {
		this.code = code;
		this.name = name;
		this.update = update;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String sectionCode) {
		this.code = sectionCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String sectionName) {
		this.name = sectionName;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

}
