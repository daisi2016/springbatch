

create table G_DICT
(
  id    NUMBER(19) not null,
  dict_code VARCHAR2(50 CHAR),
  dict_name  VARCHAR2(255 CHAR),
  value  VARCHAR2(50 CHAR),
  lable  VARCHAR2(255 CHAR),
  create_date  VARCHAR2(10 CHAR),
  create_time  VARCHAR2(10 CHAR),
  update_date  VARCHAR2(10 CHAR),
  update_time  VARCHAR2(10 CHAR)
);

alter table G_DICT   add primary key (id);

 comment on table G_DICT is '统一账号字典表';
 comment on column G_DICT.id is '主键,字典编号';
 comment on column G_DICT.dict_code is '字典代码';
 comment on column G_DICT.dict_name is '字典名称';
 comment on column G_DICT.value is '字典项值';
 comment on column G_DICT.lable is '字典项名称';


create table G_DICT_MAPPING
(
  id    NUMBER(19) not null,
  value  VARCHAR2(50 CHAR),
  lable  VARCHAR2(255 CHAR),
  sys_type  NUMBER(4),
  dict_id NUMBER(19),
  create_date  VARCHAR2(10 CHAR),
  create_time  VARCHAR2(10 CHAR),
  update_date  VARCHAR2(10 CHAR),
  update_time  VARCHAR2(10 CHAR)
);

alter table G_DICT_MAPPING   add primary key (id);

comment on table G_DICT_MAPPING is '统一账号字典表和业务系统字典映射表';
 comment on column G_DICT_MAPPING.id is '主键';
 comment on column G_DICT_MAPPING.value is '业务系统字典项值';
 comment on column G_DICT_MAPPING.lable is '业务系统字典项名称';
 comment on column G_DICT_MAPPING.sys_type is '业务系统';
 comment on column G_DICT_MAPPING.dict_id is '映射字典编号';
 
 CREATE SEQUENCE SEQ_G_DICT 
	INCREMENT BY 1 
	START WITH 1 
	NOMAXvalue 
	NOCYCLE 
	NOCACHE;
	
CREATE SEQUENCE SEQ_G_DICT_MAPPING 
	INCREMENT BY 1 
	START WITH 1 
	NOMAXvalue 
	NOCYCLE 
	NOCACHE;
	
create index I_G_DICT ON  G_DICT(dict_code);

create index I_G_DICT_MAPPING ON  G_DICT_MAPPING(sys_type,dict_id);