package com.hx.data.center.constants;


public enum GeneralBuizType {

	JZJY(1000, "集中交易"),
	RZRQ(1001, "融资融券"),
	OTC(1002, "个股期权"),
	GGQQ(1003, "OTC"),
	GJS(1004, "贵金属"),
	CRM4(1005, "CRM4"),
	NSJ(1006, "N世界"),
	SC(1007,"商城积分账户");
	
	public final Integer code;
	public final String name;
	
	GeneralBuizType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static String getNameByCode(Integer code) {
		
		for(GeneralBuizType em:values()) {
			if(em.code==(code)) {
				return em.name;
			}
		}
		return "";
	}
	
	


}
