package com.development.backend.ng.springmvc.bean;

import java.util.List;

public class ResultBean {

	private String Description;
	
	private String text;

	private String code;

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
