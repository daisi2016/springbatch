create table G_CUST_INFO
(
  cust_id    NUMBER(19) not null,
  full_name VARCHAR2(255 CHAR),
  cert_type  NUMBER(2),
  cert_no  VARCHAR2(255 CHAR),
  ymt  VARCHAR2(25 CHAR),
  cust_type  NUMBER(1),
  create_date  VARCHAR2(10 CHAR),
  create_time  VARCHAR2(10 CHAR),
  update_date  VARCHAR2(10 CHAR),
  update_time  VARCHAR2(10 CHAR)
);

alter table G_CUST_INFO   add primary key (cust_id);

 comment on table G_CUST_INFO is '统一账号信息表';
 comment on column G_CUST_INFO.cust_id is '统一账号';
 comment on column G_CUST_INFO.full_name is '客户全称';
 comment on column G_CUST_INFO.cert_type is '证件类别';
 comment on column G_CUST_INFO.cert_no is '证件号码';
 comment on column G_CUST_INFO.ymt is '一码通号';
 comment on column G_CUST_INFO.cust_type is '个人/机构';
 
  
  create table G_CUST_INFO_REl
(
  ID    NUMBER(19) not null,
  cust_no VARCHAR2(50 CHAR),
  sys_type  NUMBER(4),
  cust_id  NUMBER(19),
  create_date  VARCHAR2(10 CHAR),
  create_time  VARCHAR2(10 CHAR),
  update_date  VARCHAR2(10 CHAR),
  update_time  VARCHAR2(10 CHAR)
);
-- status NUMBER(2),

alter table G_CUST_INFO_REl  add primary key (ID);

 comment on table G_CUST_INFO_REl is '统一账号和业务资金号关联表';
 comment on column G_CUST_INFO_REl.ID is 'pk';
 comment on column G_CUST_INFO_REl.cust_no is '客户资金号';
 comment on column G_CUST_INFO_REl.sys_type is '所属业务系统';
 --comment on column G_CUST_INFO_REl.status is '账户状态,正常/销户';
 comment on column G_CUST_INFO_REl.cust_id is '统一账号';
 
 
 CREATE SEQUENCE SEQ_GCUST_INFO 
	INCREMENT BY 1 
	START WITH 80000 
	NOMAXvalue 
	NOCYCLE 
	NOCACHE;
	
CREATE SEQUENCE SEQ_GCUST_INFO_REl 
	INCREMENT BY 1 
	START WITH 1 
	NOMAXvalue 
	NOCYCLE 
	NOCACHE;
	
create index I_CUST_INFO ON  G_CUST_INFO(full_name,cert_type,cert_no);

create index I_CUST_INFO1 ON  G_CUST_INFO(ymt);

create index I_CUST_INFO_REL ON  G_CUST_INFO_REL(cust_no,sys_type);
create index I_CUST_INFO_REL1 ON  G_CUST_INFO_REL(cust_id);
 
 --处理所有客户，包括销户客户，
 --客户三要素或者一码通相同就认为是相同账户
 --1003 otc账户也认为是普通账户
 
