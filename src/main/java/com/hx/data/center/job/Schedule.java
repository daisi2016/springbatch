package com.hx.data.center.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hx.data.center.service.GeneralCustDispatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configurable
@EnableScheduling
@Component
public class Schedule {  

    @Autowired
    private GeneralCustDispatchService generalCustDispatchService;
    @Value("${general.cust.job.run}")
    private boolean generalFlag;
    
    /**
     * 统一客户号job,0 0 01 * * ?
     */
    @Scheduled(cron = "${general.cust.job.cron}")
    public void generalCustDivdJob() {
    	if(!generalFlag) {
    		//控制运不运行任务
    		return ;
    	}
    	try {
			generalCustDispatchService.dispatchWork();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("统一客户任务调度异常,"+e.getMessage());
		}
    	
    }
    
}
