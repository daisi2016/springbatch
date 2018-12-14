package com.hx.data.center.batch;

import com.hx.data.center.entity.GeneralCifCustBo;
import com.hx.data.center.entity.GeneralCustBo;
import com.hx.data.center.mapper.GeneralCustMapper;
import com.hx.data.center.util.SimpleUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
@Slf4j
public class BatchTaskConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Bean
    public Job generalCustNoJob() {
        return jobBuilderFactory.get("generalCustNoJob")
                .start(cifCustStep())
                .build();
    }

    /**统一账户处理job
     * @return
     */
    @Bean
    public Step cifCustStep() {
        return stepBuilderFactory.get("cifCustStep")
                .<GeneralCifCustBo, GeneralCustBo>chunk(5000)
                .reader(cifCustReader())
                .processor(cifCustProccess())
                .writer(cifCusWrite())
                .faultTolerant()
                //.skipLimit(10)                           //可以异常跳过10次，多了就任务失败
                .skip(NullPointerException.class)
                .build();
    }
   /* @Bean
    @StepScope
    public ItemReader<GeneralCifCustBo> cifCustReader() {

        ItemReader<GeneralCifCustBo>   itemReader = new  ItemReader<GeneralCifCustBo>(){
            @Override
            public GeneralCifCustBo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                GeneralCifCustBo generalCifCustBo = cifCustMainReader().read();
                if(generalCifCustBo.getCertType()==19) {
                    //19	香港居民身份证	--->16	港澳台居民身份证
                    generalCifCustBo.setCertType(16);
                }
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
                return generalCifCustBo;
            }
        };


        return itemReader;
    }*/

    @Bean
    @StepScope
    public ItemReader<GeneralCifCustBo> cifCustReader() {
        MyBatisPagingItemReader<GeneralCifCustBo> itemReader = new MyBatisPagingItemReader<GeneralCifCustBo>();
        itemReader.setQueryId("com.hx.data.center.mapper.GeneralCustMapper.getCIFCust");
        itemReader.setPageSize(200);
        itemReader.setSqlSessionFactory(sqlSessionFactory);
        try {
            itemReader.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemReader;
    }

    @Bean
    @StepScope
    public ItemWriter<GeneralCustBo> custMainWriter() {
        MyBatisBatchItemWriter<GeneralCustBo> itemWriter = new MyBatisBatchItemWriter<GeneralCustBo>();
        sqlSessionFactory.openSession(ExecutorType.BATCH);
        SqlSessionTemplate temp  = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        itemWriter.setSqlSessionFactory(sqlSessionFactory);
        itemWriter.setSqlSessionTemplate(temp);
        itemWriter.setStatementId("com.hx.data.center.mapper.GeneralCustMapper.saveGeneralCust");
        return itemWriter;
    }
    @Bean
    @StepScope
    public ItemWriter<GeneralCustBo> custRelWriter() {
        MyBatisBatchItemWriter<GeneralCustBo> itemWriter = new MyBatisBatchItemWriter<GeneralCustBo>();
        sqlSessionFactory.openSession(ExecutorType.BATCH);
        SqlSessionTemplate temp  = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);;
        itemWriter.setSqlSessionFactory(sqlSessionFactory);
        itemWriter.setSqlSessionTemplate(temp);
        itemWriter.setStatementId("com.hx.data.center.mapper.GeneralCustMapper.saveGeneralCustRelation");
        return itemWriter;
    }

    /**账户关系落地
     * @return ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<GeneralCustBo> cifCusWrite(){
        ItemWriter writer =    new ItemWriter<GeneralCustBo>(){
            @Override
            public void write(List<? extends GeneralCustBo> list) throws Exception {
                if(SimpleUtil.isNotEmpty(list)){
                    List<GeneralCustBo> mainCustList = new ArrayList();
                    List<GeneralCustBo> relCustList = new ArrayList();
                    list.forEach(generalCustBo->{
                        if("UPDATE".equals(generalCustBo.getActive())){
                            relCustList.add(generalCustBo);

                        }else if("CREATE".equals(generalCustBo.getActive())){
                            //保存统一客户号
                            mainCustList.add(generalCustBo);
                            //映射关系保存
                            GeneralCustBo relationBo = new GeneralCustBo();
                            relationBo.setCustId(generalCustBo.getCustId());
                            relationBo.setCustNo(generalCustBo.getCustNo());
                            relationBo.setSysType(generalCustBo.getSysType());
                            relationBo.setStatus(generalCustBo.getStatus());
                            //保存关联
                            relCustList.add(relationBo);
                        }
                    });
                    if(SimpleUtil.isNotEmpty(mainCustList)){
                        try {
                            custMainWriter().write(mainCustList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(SimpleUtil.isNotEmpty(relCustList)){
                        try {
                            custRelWriter().write(mainCustList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                log.info("处理批次，条数---"+list.size()+"时间"+ SimpleUtil.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
            }
        };
        return writer;
    }

    /**cif账户处理
     * @return
     */
    @Bean
    @StepScope
    public ItemProcessor<GeneralCifCustBo, GeneralCustBo> cifCustProccess(){
        ItemProcessor<GeneralCifCustBo,GeneralCustBo> itemProcessor =    new ItemProcessor<GeneralCifCustBo,GeneralCustBo>(){
            @Override
            public GeneralCustBo process(GeneralCifCustBo generalCifCustBo) throws Exception {
                if(generalCifCustBo.getCertType()==19) {
                    //19	香港居民身份证	--->16	港澳台居民身份证
                    generalCifCustBo.setCertType(16);
                }
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
//                List<GeneralCustBo> list = mapper.getGeneralCust(generalCifCustBo);
                List<GeneralCustBo> list =getRelCustDettail(generalCifCustBo);
                if (SimpleUtil.isNotEmpty(list)) {
                    // 存在，判断是否已近挂接
                    boolean flag = true;
                    for (GeneralCustBo generalCustBo : list) {
                        if (generalCifCustBo.getSysType().equals(generalCustBo.getSysType())
                                && generalCustBo.getCustNo().equals(generalCifCustBo.getCustNo())) {
                            flag = false;
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
                        tempBo.setActive("UPDATE");
                        return tempBo;
                    }else{
                        return null;
                    }
                } else {
                    // 不存在，创建统一客户号并挂接该资金号
                    GeneralCustBo generalBo = new GeneralCustBo();
                    generalBo.setCertNo(generalCifCustBo.getCertNo());
                    generalBo.setCertType(generalCifCustBo.getCertType());
                    generalBo.setFullName(generalCifCustBo.getFullName());
                    generalBo.setYmt(generalCifCustBo.getYmt());
                    generalBo.setCustType(generalCifCustBo.getCustType());
                    Integer custId =getGeneralCustNo();
                    generalBo.setCustNo(generalCifCustBo.getCustNo());
                    generalBo.setCustId(custId);
                    generalBo.setSysType(generalCifCustBo.getSysType());
                    generalBo.setStatus(generalCifCustBo.getStatus());
                    generalBo.setActive("CREATE");
                    //保存统一客户号
                    return  generalBo;
                }
            }
        };
        return itemProcessor;
    }

    /**获取客户
     * @param generalCifCustBo
     * @return
     */
    public  List<GeneralCustBo>  getRelCustDettail(GeneralCifCustBo generalCifCustBo) {
        List<GeneralCustBo> list= new ArrayList<>();
        MyBatisPagingItemReader<GeneralCustBo> itemReader = getRelCustDettailReader();
        Map<String,Object>param = new HashMap<>();
        param.put("generalCifCustBo",generalCifCustBo);
        itemReader.setParameterValues(param);
        try {
            itemReader.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExecutionContext executionContext = new ExecutionContext();
        itemReader.open(executionContext);
        GeneralCustBo temp = new GeneralCustBo();
        while(temp != null){
            try {
                temp = itemReader.read();
                if(temp!=null){
                    list.add(temp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        itemReader.close();
        return list;
    }

    @Bean
    @StepScope
    public MyBatisPagingItemReader<GeneralCustBo>   getRelCustDettailReader() {
        MyBatisPagingItemReader<GeneralCustBo> itemReader = new MyBatisPagingItemReader<GeneralCustBo>();
        itemReader.setQueryId("com.hx.data.center.mapper.GeneralCustMapper.getGeneralCustDetail");
        itemReader.setPageSize(200);
        itemReader.setSqlSessionFactory(sqlSessionFactory);
        return itemReader;
    }

    /**获取统一客户号，过滤末尾是四的数字
     * @return
     */
    public  Integer  getGeneralCustNo() {
        MyBatisPagingItemReader<Integer> itemReader = getGeneralCustNoReader();

        ExecutionContext executionContext = new ExecutionContext();
        try {
            itemReader.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemReader.open(executionContext);
        Integer tempCustNo  = -99;
        while(tempCustNo != null){
            try {
                tempCustNo = itemReader.read();
                if(tempCustNo!=null){
                    String tempCustNoStr = tempCustNo.toString();
                    if(!tempCustNoStr.endsWith("4")) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        itemReader.close();
        return tempCustNo;
    }

    @Bean
    @StepScope
    public  MyBatisPagingItemReader<Integer>  getGeneralCustNoReader() {
        MyBatisPagingItemReader<Integer> itemReader = new MyBatisPagingItemReader<Integer>();
        itemReader.setQueryId("com.hx.data.center.mapper.GeneralCustMapper.getGeneralCustNo");
        itemReader.setPageSize(1);
        itemReader.setSqlSessionFactory(sqlSessionFactory);


        return itemReader;
    }


}





