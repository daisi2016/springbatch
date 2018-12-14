package com.hx.data.center.service;

import java.util.List;

import com.hx.data.center.entity.GeneralCifCustBo;

public interface GeneralCustService {
	
	 /**获取Cif待处理记录数
     * @return
     */
    public Long  getCIFCustNum();
    
    /**分页查询Cif用户信息
     * @param start
     * @param end
     * @return
     */
    public List<GeneralCifCustBo> getCIFCustPagesize( int start,  int end);
    

	 /**获取Crm待处理记录数
    * @return
    */
   public Long  getCrmCustNum();
   
   /**分页查询Crm用户信息
    * @param start
    * @param end
    * @return
    */
   public List<GeneralCifCustBo> getCrmCustPagesize( int start,  int end);
    


	/**分配统一客户号
	 * @param bo
	 */
	public void divdGeneralCustNo(GeneralCifCustBo bo) ;

}
