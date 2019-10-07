package com.pajf.dm.enums;

/**
 * process constant enum
 * @author louie
 * @date created in 2019-10-6 12:12
 */
public enum ProcessEnum {
	/**
	 * 外事服务流程定义id
	 */
	FOREIGN_PROCESS_ID("obj_f2b720c367f44292a8ea2f8749e18302", "foreign process version id")
	;

	private String value;
	private String name;

	ProcessEnum(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
