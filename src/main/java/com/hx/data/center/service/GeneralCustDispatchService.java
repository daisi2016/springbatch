package com.hx.data.center.service;

/**统一客户号调度任务
 * @author daisi
 *
 */
public interface GeneralCustDispatchService {
	
	/**
	 * 第一次初始化统一账户
	 */
	public void dispatchWorkInit(int period) ;
	
	/**
	 * 每日定时任务调度处理统一账户
	 */
	public void dispatchWork() ;

}
