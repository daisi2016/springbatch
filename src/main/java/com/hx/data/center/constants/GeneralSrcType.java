package com.hx.data.center.constants;


public enum GeneralSrcType {

	CRM("CRM", "CRM"),
	CIF("CIF","CIF");
	
	public final String code;
	public final String name;
	
	GeneralSrcType(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static String getNameByCode(String code) {
		
		for(GeneralSrcType em:values()) {
			if(em.code.equals(code)) {
				return em.name;
			}
		}
		return "";
	}
	
	


}
