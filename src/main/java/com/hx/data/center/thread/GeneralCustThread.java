package com.hx.data.center.thread;

import java.util.List;
import java.util.concurrent.Callable;

import com.hx.data.center.constants.GeneralBuizType;
import com.hx.data.center.constants.GeneralSrcType;
import com.hx.data.center.entity.GeneralCifCustBo;
import com.hx.data.center.service.GeneralCustService;

import lombok.extern.slf4j.Slf4j;

/**
 * 标签处理线程
 * 
 * @author si.dai
 *
 */
@Slf4j
public class GeneralCustThread implements Callable<String> {
	private List<GeneralCifCustBo> custList;
	private GeneralCustService generalCustService;
	private String srcType;
 

	public GeneralCustThread(List<GeneralCifCustBo> custList, GeneralCustService generalCustService, String srcType) {
		super();
		this.custList = custList;
		this.generalCustService = generalCustService;
		this.srcType = srcType;
	}


	@Override
	public String call() throws Exception {
		for (GeneralCifCustBo bo : custList) {
			try {
				if(GeneralSrcType.CIF.code.equals(srcType)&&bo.getCertType()==19) {
						//19	香港居民身份证	--->16	港澳台居民身份证	
					bo.setCertType(16);
				}else if(GeneralSrcType.CRM.code.equals(srcType)) {
					bo.setStatus(0);
					bo.setSysType(GeneralBuizType.CRM4.code);
				}
				generalCustService.divdGeneralCustNo(bo);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("统一账户处理失败," + custList.toString());
				// 处理失败返回失败数据交由调度层处理
			}
		}
		return null;
	}

}
