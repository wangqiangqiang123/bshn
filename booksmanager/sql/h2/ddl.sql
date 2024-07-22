-- Table structure for book 图书表、book_user_ref 读者图书关联关系表、lend_record 借阅记录表、b_user 用户表、role 角色表、auth 权限表、user_role_ref 用户角色关系表、role_auth_ref 角色权限关系表

DROP TABLE IF EXISTS book;
CREATE TABLE book  (
  id bigint NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  book_no varchar(255)  NOT NULL COMMENT '图书编号',
  name varchar(255)  DEFAULT NULL COMMENT '名称',
  price decimal(10, 2) DEFAULT NULL COMMENT '价格',
  author varchar(255)  DEFAULT NULL COMMENT '作者',
  publisher varchar(255)  DEFAULT NULL COMMENT '出版社',
  publish_time datetime DEFAULT NULL COMMENT '出版时间',
  status varchar(1)  NOT NULL DEFAULT '1' COMMENT '0：未借出 1：已借出',
  update_time datetime DEFAULT NULL COMMENT '修改时间',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(255)  NULL DEFAULT NULL COMMENT '修改人',
  create_by varchar(255)  NULL DEFAULT NULL COMMENT '创建人',
  update_id bigint  COMMENT '修改人id',
  create_id bigint  COMMENT '创建人id',
  PRIMARY KEY (id) ,
  UNIQUE (book_no)
);



DROP TABLE IF EXISTS reader_book_ref;
CREATE TABLE reader_book_ref  (
  id bigint NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  reader_id bigint NOT NULL COMMENT '读者id',
  book_id bigint NOT NULL COMMENT '图书id',
  book_no varchar(255)  NOT NULL COMMENT '图书编号',
  lend_time datetime DEFAULT NULL COMMENT '借阅时间',
  dead_time datetime DEFAULT NULL COMMENT '应归还时间',
  update_time datetime  DEFAULT NULL COMMENT '修改时间',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(255)  DEFAULT NULL COMMENT '修改人',
  create_by varchar(255)  DEFAULT NULL COMMENT '创建人',
  update_id bigint  COMMENT '修改人id',
  create_id bigint  COMMENT '创建人id',
  PRIMARY KEY (id) ,
  UNIQUE (book_id )
) ;

CREATE index `cbreader_id_index` on reader_book_ref(`reader_id`);
CREATE index `cbbook_no_index` on reader_book_ref(`book_no`);


DROP TABLE IF EXISTS `lend_record`;
CREATE TABLE `lend_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `reader_id` bigint NOT NULL COMMENT '读者id',
  `book_id` bigint NOT NULL COMMENT '图书id',
  `book_no` varchar(255)  NOT NULL COMMENT '图书编号',
  `lend_time` datetime DEFAULT NULL COMMENT '借书日期',
  `return_time` datetime DEFAULT NULL COMMENT '还书日期',
  `status` varchar(1)  DEFAULT NULL COMMENT '0：未归还 1：已归还',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255)  DEFAULT NULL COMMENT '修改人',
  `create_by` varchar(255)  DEFAULT NULL COMMENT '创建人',
  `update_id` bigint  COMMENT '修改人id',
  `create_id` bigint  COMMENT '创建人id',
   PRIMARY KEY (`id`)
) ;
CREATE index `bareader_id_index` on lend_record(`reader_id`);
CREATE index `babook_no_index` on lend_record(`book_no`);
CREATE index `babook_id_index` on lend_record(`book_id`);


DROP TABLE IF EXISTS b_user;
CREATE TABLE b_user  (
  id IDENTITY(10, 2)  COMMENT 'ID',
  username varchar(255)  DEFAULT NULL COMMENT '用户名',
  password varchar(255)  DEFAULT NULL COMMENT '密码',
  nick_name varchar(255)  DEFAULT NULL COMMENT '姓名',
  phone varchar(255)  DEFAULT NULL COMMENT '电话号码',
  email varchar(255)  DEFAULT NULL COMMENT '邮箱',
  sex varchar(255)  DEFAULT NULL COMMENT '性别',
  address varchar(255)  DEFAULT NULL COMMENT '住址',
  update_time datetime DEFAULT NULL COMMENT '修改时间',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(255)  DEFAULT NULL COMMENT '修改人',
  create_by varchar(255)  DEFAULT NULL COMMENT '创建人',
  update_id bigint  COMMENT '修改人id',
  create_id bigint  COMMENT '创建人id',
  PRIMARY KEY (id)
) ;


DROP TABLE IF EXISTS role;
CREATE TABLE role  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  rolename varchar(255)  DEFAULT NULL COMMENT '角色名称',
  rolecode varchar(255)  NOT NULL COMMENT '角色编码',
  update_time datetime DEFAULT NULL COMMENT '修改时间',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(255)  DEFAULT NULL COMMENT '修改人',
  create_by varchar(255)  DEFAULT NULL COMMENT '创建人',
  update_id bigint  COMMENT '修改人id',
  create_id bigint  COMMENT '创建人id',
  PRIMARY KEY (id) ,
  UNIQUE (rolecode)
) ;



DROP TABLE IF EXISTS auth;
CREATE TABLE auth  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  module_name varchar(255)  DEFAULT NULL COMMENT '权限模块名称',
  module_code varchar(255)   DEFAULT NULL COMMENT '权限模块编码',
  auth_name varchar(255)  DEFAULT NULL COMMENT '权限名称',
  auth_type varchar(255)  DEFAULT NULL COMMENT '权限类型',
  menu_name varchar(255)  DEFAULT NULL COMMENT '菜单名称',
  menu_url varchar(255)  DEFAULT NULL COMMENT '菜单路径',
  update_time datetime DEFAULT NULL COMMENT '修改时间',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(255)  DEFAULT NULL COMMENT '修改人',
  create_by varchar(255)  DEFAULT NULL COMMENT '创建人',
  update_id bigint  COMMENT '修改人id',
  create_id bigint  COMMENT '创建人id',
  PRIMARY KEY (id)
) ;



DROP TABLE IF EXISTS user_role_ref;
CREATE TABLE user_role_ref  (
  id IDENTITY(10, 2)  COMMENT 'ID',
  user_id bigint NOT NULL COMMENT '用户id',
  role_id bigint NOT NULL COMMENT '角色id',
  update_time datetime DEFAULT NULL COMMENT '修改时间',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(255)  DEFAULT NULL COMMENT '修改人',
  create_by varchar(255)  DEFAULT NULL COMMENT '创建人',
  update_id bigint  COMMENT '修改人id',
  create_id bigint  COMMENT '创建人id',
  PRIMARY KEY (id)
) ;

CREATE index `ebgrole_id_index` on user_role_ref(`role_id`);
CREATE index `ebguser_id_index` on user_role_ref(`user_id`);


DROP TABLE IF EXISTS role_auth_ref;
CREATE TABLE role_auth_ref  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  role_id bigint NOT NULL COMMENT '角色id',
  auth_id bigint NOT NULL COMMENT '权限id',
  update_time datetime DEFAULT NULL COMMENT '修改时间',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(255)  DEFAULT NULL COMMENT '修改人',
  create_by varchar(255)  DEFAULT NULL COMMENT '创建人',
  update_id bigint  COMMENT '修改人id',
  create_id bigint  COMMENT '创建人id',
  PRIMARY KEY (id)
) ;

CREATE index `hhkarole_id_index` on role_auth_ref(`role_id`);
CREATE index `hhkauth_id_index` on role_auth_ref(`auth_id`);
