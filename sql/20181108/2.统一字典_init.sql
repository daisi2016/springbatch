insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval,'BANKS', '银行', '0001', '平安银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval,'BANKS', '银行', '0002', '兴业银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval,'BANKS', '银行', '0003', '上海银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval,'BANKS', '银行', '0004', '中国银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval,'BANKS', '银行', '0005', '工商银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval,'BANKS', '银行', '0006', '广发银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval,'BANKS', '银行', '0007', '建设银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'BANKS', '银行', '0008', '交通银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'BANKS', '银行', '0009', '农业银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'BANKS', '银行', '0010', '浦发银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'BANKS', '银行', '0011', '招商银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'BANKS', '银行', '0012', '中信银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'BANKS', '银行', '0013', '光大银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'BANKS', '银行', '0014', '华夏银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'BANKS', '银行', '0015', '民生银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'BANKS', '银行', '0016', '宁波银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'BANKS', '银行', '0017', '南京银行', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'CURRENCY', '币种', 'RMB', '人民币', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'CURRENCY', '币种', 'USD', '美元', '20181108');
insert into G_DICT (id, dict_code, dict_name, value, lable, create_date)values (seq_g_dict.nextval, 'CURRENCY', '币种', 'HKD', '港币', '20181108');







--集中交易银行
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'PAYH','平安银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0001'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYYH','兴业银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0002'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'SHYH','上海银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0003'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'ZGYH','中国银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0004'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'GSYH','工商银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0005'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'GFYH','广发银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0006'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'JSYH','建设银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0007'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'JTYH','交通银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0008'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'NYYH','农业银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0009'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'PFYH','浦发银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0010'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'ZSYH','招商银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0011'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'ZXYH','中信银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0012'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'GDYH','光大银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0013'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'HXYH','华夏银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0014'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'MSYH','民生银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0015'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'NBYH','宁波银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0016'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'NJYH','南京银行',1000,(select id  from g_dict where  dict_code='BANKS' and value='0017'),'201801108');


--个股期权银行

insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'YYZH','银衍中行',1002,(select id  from g_dict where  dict_code='BANKS' and value='0004'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'YYGH','银衍工行',1002,(select id  from g_dict where  dict_code='BANKS' and value='0005'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'YYJH','银衍建行',1002,(select id  from g_dict where  dict_code='BANKS' and value='0007'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'YYJT','银衍交行',1002,(select id  from g_dict where  dict_code='BANKS' and value='0008'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'YYNH','银衍农行',1002,(select id  from g_dict where  dict_code='BANKS' and value='0009'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'YYPF','银衍浦发',1002,(select id  from g_dict where  dict_code='BANKS' and value='0010'),'201801108');

--融资融券银行
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYXY','信用兴业',1001,(select id  from g_dict where  dict_code='BANKS' and value='0002'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYSH','信用上行',1001,(select id  from g_dict where  dict_code='BANKS' and value='0003'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYZH','信用中行',1001,(select id  from g_dict where  dict_code='BANKS' and value='0004'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYGH','信用工行',1001,(select id  from g_dict where  dict_code='BANKS' and value='0005'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYJS','信用建行',1001,(select id  from g_dict where  dict_code='BANKS' and value='0007'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYJT','信用交行',1001,(select id  from g_dict where  dict_code='BANKS' and value='0008'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYNY','信用农业',1001,(select id  from g_dict where  dict_code='BANKS' and value='0009'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYPF','信用浦发',1001,(select id  from g_dict where  dict_code='BANKS' and value='0010'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYZS','信用招行',1001,(select id  from g_dict where  dict_code='BANKS' and value='0011'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'XYZX','信用中信',1001,(select id  from g_dict where  dict_code='BANKS' and value='0012'),'201801108');

--币种数据
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'RMB','人民币',1000,(select id  from g_dict where  dict_code='CURRENCY' and value='RMB'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'USD','美元',1000,(select id  from g_dict where  dict_code='CURRENCY' and value='USD'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'HKD','港币',1000,(select id  from g_dict where  dict_code='CURRENCY' and value='HKD'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'RMB','人民币',1001,(select id  from g_dict where  dict_code='CURRENCY' and value='RMB'),'201801108');
insert into g_dict_mapping(id, value, lable, sys_type, dict_id, create_date)values(SEQ_G_DICT_MAPPING.nextval,'RMB','人民币',1002,(select id  from g_dict where  dict_code='CURRENCY' and value='RMB'),'201801108');