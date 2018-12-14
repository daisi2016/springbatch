package com.hx.data.center.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hx.data.center.entity.GeneralCifCustBo;
import com.hx.data.center.entity.GeneralCustBo;

public interface GeneralCustMapper {
	
    
    /**获取待处理记录数
     * @return
     */
    public Long  getCIFCustNum();
    
    /**分页查询用户信息
     * @param start
     * @param end
     * @return
     */
    public List<GeneralCifCustBo> getCIFCustPagesize(@Param("start") int start, @Param("end") int end);

    public List<GeneralCifCustBo> getCIFCust(Map<String,Object> params);

    /**获取待处理记录数
     * @return
     */
    public Long  getCrmCustNum();
    
    /**分页查询用户信息
     * @param start
     * @param end
     * @return
     */
    public List<GeneralCifCustBo> getCrmCustPagesize(@Param("start") int start, @Param("end") int end);

    /**查询满足条件的一码通信息
     * @param generalCifCustBo
     * @return
     */
    public List<GeneralCustBo> getGeneralCust(@Param("generalCifCustBo")GeneralCifCustBo generalCifCustBo);
    
    /**获取统一客户号
     * @return
     */
    public Integer getGeneralCustNo();
    
    public void saveGeneralCust(GeneralCustBo generalCustBo);
    public void getGeneralCustDetail(GeneralCustBo generalCustBo);

    public void saveGeneralCustRelation( GeneralCustBo generalCustBo);
    public void updateGeneralCustRelationStatus(GeneralCustBo generalCustBo);

}
