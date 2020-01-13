package com.seadun.midi.aas.entity;

public class ConditionAttr {
	private String dbAttr;
	private String jsbAttr;

	public ConditionAttr(String dbAttr, String jsbAttr) {
		this.dbAttr = dbAttr;
		this.jsbAttr = jsbAttr;
	}

	public String getDbAttr() {
		return dbAttr;
	}

	public void setDbAttr(String dbAttr) {
		this.dbAttr = dbAttr;
	}

	public String getJsbAttr() {
		return jsbAttr;
	}

	public void setJsbAttr(String jsbAttr) {
		this.jsbAttr = jsbAttr;
	}

}
