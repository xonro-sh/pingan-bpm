package com.pajf.dm.enums;

/**
 * bo constant enum
 * @author louie
 * @date created in 2019-10-6 12:17
 */
public enum BoEnum {
	/**
	 * 外事服务DW BO名称
	 */
	FOREIGN_DW ("BO_DM_ZFX_WSFW", "foreign service DW bo name"),
	/**
	 * 外事服务流程BO名称
	 */
	FOREIGN_PROCESS_MAIN("BO_DM_ZFX_WSFW_WF", "foreign service process bo name")
	;

	BoEnum(String value, String name) {
		this.value = value;
		this.name = name;
	}

	private String value;
	private String name;

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
