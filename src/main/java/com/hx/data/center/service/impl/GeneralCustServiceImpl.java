package com.hx.data.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hx.data.center.entity.GeneralCifCustBo;
import com.hx.data.center.entity.GeneralCustBo;
import com.hx.data.center.mapper.GeneralCustMapper;
import com.hx.data.center.service.GeneralCustService;
import com.hx.data.center.util.SimpleUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GeneralCustServiceImpl implements GeneralCustService {
	@Autowired
	private GeneralCustMapper mapper;

	@Override
	public void divdGeneralCustNo(GeneralCifCustBo generalCifCustBo) {
		// 判断是否存在统一客户号（三要素和一码通）
		if(generalCifCustBo.getCustType()==0) {
			//个人全称不规范，用简称替换
			generalCifCustBo.setFullName(generalCifCustBo.getName());
		}
		
		if(SimpleUtil.isBlank(generalCifCustBo.getYmt())) {
			generalCifCustBo.setYmt("");
		}
		if(SimpleUtil.isBlank(generalCifCustBo.getCertNo())) {
			generalCifCustBo.setCertNo("");
		}
		if(SimpleUtil.isBlank(generalCifCustBo.getFullName())) {
			generalCifCustBo.setFullName("");
		}
		 
		List<GeneralCustBo> list = mapper.getGeneralCust(generalCifCustBo);
		if (SimpleUtil.isNotEmpty(list)) {
			// 存在，判断是否已近挂接
			boolean flag = true;
			for (GeneralCustBo generalCustBo : list) {
				if (generalCifCustBo.getSysType().equals(generalCustBo.getSysType())
						&& generalCustBo.getCustNo().equals(generalCifCustBo.getCustNo())) {
					flag = false;
					/*//已挂接信息有变化，更新信息
					if(generalCustBo.getStatus()!=generalCifCustBo.getStatus()) {
						generalCustBo.setStatus(generalCifCustBo.getStatus());
						mapper.updateGeneralCustRelationStatus(generalCustBo);
						flag = false;
					}*/
					break;
				}
			}
			if (flag) {
				//挂接
				GeneralCustBo tempBo = new GeneralCustBo();
				tempBo.setCustId(list.get(0).getCustId());
				tempBo.setCustNo(generalCifCustBo.getCustNo());
				tempBo.setSysType(generalCifCustBo.getSysType());
				tempBo.setStatus(generalCifCustBo.getStatus());
				mapper.saveGeneralCustRelation(tempBo);
			} 
		} else {
			// 不存在，创建统一客户号并挂接该资金号
			GeneralCustBo generalBo = new GeneralCustBo();
			if(SimpleUtil.isBlank(generalCifCustBo.getCertNo())) {
				generalBo.setCertNo("");
			}else {
				generalBo.setCertNo(generalCifCustBo.getCertNo());
			}
			if(generalCifCustBo.getCertType()==null) {
				generalBo.setCertType(0);
			}else {
				generalBo.setCertType(generalCifCustBo.getCertType());
			}
			if(SimpleUtil.isBlank(generalCifCustBo.getFullName())) {
				generalBo.setFullName("");
			}else {
				generalBo.setFullName(generalCifCustBo.getFullName());
			}
			if(SimpleUtil.isBlank(generalCifCustBo.getYmt())) {
				generalBo.setYmt("");
			}else {
				generalBo.setYmt(generalCifCustBo.getYmt());
			}
			generalBo.setCustType(generalCifCustBo.getCustType());
			Integer custId =getGeneralCustNo();
			generalBo.setCustId(custId);
			//保存统一客户号
			mapper.saveGeneralCust(generalBo);
			
			//映射关系保存
			GeneralCustBo tempBo = new GeneralCustBo();
			tempBo.setCustId(generalBo.getCustId());
			if(SimpleUtil.isBlank(generalCifCustBo.getCustNo())) {
				tempBo.setCustNo("");
			}else {
				tempBo.setCustNo(generalCifCustBo.getCustNo());
			}
			tempBo.setSysType(generalCifCustBo.getSysType());
			tempBo.setStatus(generalCifCustBo.getStatus());
			//保存关联
			mapper.saveGeneralCustRelation(tempBo);
		}

	}
	
	/**获取统一客户号，过滤末尾是四的数字
	 * @return
	 */
	private Integer getGeneralCustNo(){
	while(true) {
		Integer tempCustNo = 	mapper.getGeneralCustNo();
		String tempCustNoStr = tempCustNo.toString();
		if(!tempCustNoStr.endsWith("4")) {
			return tempCustNo;
		 } 
	}
	
	}

	@Override
	public Long getCIFCustNum() {
		return mapper.getCIFCustNum();
	}

	@Override
	public List<GeneralCifCustBo> getCIFCustPagesize(int start, int end) {
		return mapper.getCIFCustPagesize(start, end);
	}

	@Override
	public Long getCrmCustNum() {
		return mapper.getCrmCustNum();
	}

	@Override
	public List<GeneralCifCustBo> getCrmCustPagesize(int start, int end) {
		return mapper.getCrmCustPagesize(start, end);
	}

}
