package com.hx.data.center.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hx.data.center.constants.GeneralSrcType;
import com.hx.data.center.entity.GeneralCifCustBo;
import com.hx.data.center.service.GeneralCustDispatchService;
import com.hx.data.center.service.GeneralCustService;
import com.hx.data.center.thread.GeneralCustThread;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GeneralCustDispatchServiceImpl implements GeneralCustDispatchService {
    @Value("${batch.per.batch.num}")
	private  int perBatchNum ;// 每个批次处理用户数
	@Value("${batch.per.thread.num}")
	private int perThreadNum ;// 每个线程处理的用户数
	@Autowired
	private GeneralCustService generalCustService;
	 
	@Override
	public void dispatchWorkInit(int period) {
		
		int perBatchNum=500;
		long custNum = generalCustService.getCIFCustNum();
		if (custNum > 0) {
			int pagesize = 0;
			if (custNum % perBatchNum > 0) {
				pagesize = (int) (custNum / perBatchNum + 1);
			} else {
				pagesize = (int) (custNum / perBatchNum);
			}
			
			for (int per = period; per < pagesize; per++) {
				long starttime = new Date().getTime();
				List<GeneralCifCustBo> custList = generalCustService.getCIFCustPagesize(per * perBatchNum, (per + 1) * perBatchNum);
					for (GeneralCifCustBo bo : custList) {
						try {
							if(bo.getCertType()==19) {
									//19	香港居民身份证	--->16	港澳台居民身份证	
								bo.setCertType(16);
							} 
							generalCustService.divdGeneralCustNo(bo);
						} catch (Exception e) {
							e.printStackTrace();
							log.error("统一账户处理失败," + custList.toString());
							// 处理失败返回失败数据交由调度层处理
						}
					}
					custList.clear();
				long endtime = new Date().getTime();
				
				log.info("-----------------------------------init-统一账户批次："+(per+1)+"处理耗时:"+(endtime-starttime)/1000+"s");
				
			}
		}
		 
		
	}
	
public void dispatchWork() {
		//处理cif 每日定时任务处理
		long custNum = generalCustService.getCIFCustNum();
		if (custNum > 0) {
			int pagesize = 0;
			if (custNum % perBatchNum > 0) {
				pagesize = (int) (custNum / perBatchNum + 1);
			} else {
				pagesize = (int) (custNum / perBatchNum);
			}
			for (int per = 0; per < pagesize; per++) {
				long starttime = new Date().getTime();
				List<GeneralCifCustBo> custList = generalCustService.getCIFCustPagesize(per * perBatchNum, (per + 1) * perBatchNum);

				if (custList != null && !custList.isEmpty()) {
					// 计算每个批次的实际处理线程数<=perBatchNum/perThreadNum
					int perThreadNum=200;
					int margin = 0;
					if (custList.size() % perThreadNum > 0) {
						margin = custList.size() / perThreadNum + 1;
					} else {
						margin = custList.size() / perThreadNum;
					}
					// 多线程批处理
					List<Future<String>> callbacks = new ArrayList<Future<String>>();
					ExecutorService pool = Executors.newCachedThreadPool();
					for (int i = 0; i < margin; i++) {
						List<GeneralCifCustBo> sublit = null;
						if (((i + 1) * perThreadNum) > custList.size()) {
							sublit = custList.subList(i * perThreadNum, custList.size());
						} else {
							sublit = custList.subList(i * perThreadNum, (i + 1) * perThreadNum);
						}
						GeneralCustThread excutorThread = new GeneralCustThread(sublit,generalCustService,GeneralSrcType.CIF.code);
						Future<String> future = pool.submit(excutorThread);
						callbacks.add(future);
					}
					pool.shutdown();
					// 线程回调
					for (Future<String> future : callbacks) {
						// 每个线程单元返回值
						try {
							future.get(60*5, TimeUnit.SECONDS);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				long endtime = new Date().getTime();
				log.info("------------------------------------Cif挂接统一账户批次："+(per+1)+"处理耗时:"+(endtime-starttime)/1000+"s");
				
			}
		}
		//处理crm客户 每日定时任务处理
		 custNum = generalCustService.getCrmCustNum();
		if (custNum > 0) {
			int pagesize = 0;
			if (custNum % perBatchNum > 0) {
				pagesize = (int) (custNum / perBatchNum + 1);
			} else {
				pagesize = (int) (custNum / perBatchNum);
			}
			for (int per = 0; per < pagesize; per++) {
				long starttime = new Date().getTime();
				List<GeneralCifCustBo> custList = generalCustService.getCrmCustPagesize(per * perBatchNum, (per + 1) * perBatchNum);

				if (custList != null && !custList.isEmpty()) {
					// 计算每个批次的实际处理线程数<=perBatchNum/perThreadNum
					int margin = 0;
					if (custList.size() % perThreadNum > 0) {
						margin = custList.size() / perThreadNum + 1;
					} else {
						margin = custList.size() / perThreadNum;
					}
					// 多线程批处理
					List<Future<String>> callbacks = new ArrayList<Future<String>>();
					ExecutorService pool = Executors.newCachedThreadPool();
					for (int i = 0; i < margin; i++) {
						List<GeneralCifCustBo> sublit = null;
						if (((i + 1) * perThreadNum) > custList.size()) {
							sublit = custList.subList(i * perThreadNum, custList.size());
						} else {
							sublit = custList.subList(i * perThreadNum, (i + 1) * perThreadNum);
						}
						GeneralCustThread excutorThread = new GeneralCustThread(sublit,generalCustService,GeneralSrcType.CRM.code);
						Future<String> future = pool.submit(excutorThread);
						callbacks.add(future);
					}
					pool.shutdown();
					// 线程回调
					for (Future<String> future : callbacks) {
						// 每个线程单元返回值
						try {
							future.get(60*5, TimeUnit.SECONDS);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				long endtime = new Date().getTime();
				log.info("------------------------------------crm4挂接统一账户批次："+(per+1)+"处理耗时:"+(endtime-starttime)/1000+"s");
				
			}
		}
		
	}

}
