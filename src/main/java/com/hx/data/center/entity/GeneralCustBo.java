package com.hx.data.center.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**统一账户信息bo
 * @author daisi
 *
 */
@Data
@Accessors(chain=true)
public class GeneralCustBo implements Serializable{
	/**
	 * select a.khh,a.khjc,a.KHMC,a.ZJLB,a.ZJBH,a.YMTH,a.KHZT,a.KHLB,b.ywxt from src_cif3.tkhxx a ,src_cif3.tywzh b  where a.khh=b.khh;
	 */
	private static final long serialVersionUID = 6103346852293688919L;
	private String custNo;
	/**
	 * 全称
	 */
	private String fullName;
	private Integer certType;
	private String certNo;
	/**
	 * 一码通
	 */
	private String ymt;
	/**
	 * 客户状态
	 */
	@Deprecated
	private Integer status;
	/**
	 * 客户类别
	 */
	private Integer custType;
	/**
	 * 所属业务系统
	 */
	private Integer sysType;
	
	/**
	 * 统一客户号
	 */
	private Integer custId;
	
	/**
	 * 关联表id
	 */
	private Integer relId;

	private String active;//UPDATE 更新挂接关系；CREATE：创建统一客户号并挂接
	
	
	

}
