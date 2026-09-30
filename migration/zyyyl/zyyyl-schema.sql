-- Generated from reference/zzyl/sql/zzyl.sql
-- Database renamed from zzyl to zyyyl; all INSERT statements removed.
-- Real personal data is not included. Liquibase remains the source of truth.


####################
##  zzyl
####################
DROP DATABASE IF EXISTS `zyyyl`;

####################
##  database zzyl ddl
####################
CREATE DATABASE `zyyyl` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

use `zyyyl`;

####################
##  bed
####################
DROP TABLE IF EXISTS `bed`;

####################
##  table bed ddl
####################
CREATE TABLE `bed` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '床位ID',
  `bed_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '床位编号',
  `bed_status` int DEFAULT NULL COMMENT '床位状态: 未入住0, 已入住1 ',
  `sort` int DEFAULT NULL COMMENT '床位号',
  `room_id` bigint DEFAULT NULL COMMENT '房间ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `bed_number` (`bed_number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='床位表';

####################
##  bed data
####################

####################
##  bed data
####################

####################
##  check_in
####################
DROP TABLE IF EXISTS `check_in`;

####################
##  table check_in ddl
####################
CREATE TABLE `check_in` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `elder_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '老人姓名',
  `elder_id` bigint NOT NULL COMMENT '老人ID',
  `id_card_no` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '身份证号',
  `start_date` date NOT NULL COMMENT '入住开始时间',
  `end_date` date DEFAULT NULL COMMENT '入住结束时间',
  `nursing_level_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '护理等级名称',
  `bed_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '入住床位',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 (0: 已入住, 1: 已退住)',
  `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序编号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

####################
##  check_in data
####################

####################
##  check_in data
####################

####################
##  check_in_config
####################
DROP TABLE IF EXISTS `check_in_config`;

####################
##  table check_in_config ddl
####################
CREATE TABLE `check_in_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_in_id` bigint NOT NULL COMMENT '入住表ID',
  `nursing_level_id` bigint NOT NULL COMMENT '护理等级ID',
  `nursing_level_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '护理等级名称',
  `fee_start_date` date NOT NULL COMMENT '费用开始时间',
  `fee_end_date` date DEFAULT NULL COMMENT '费用结束时间',
  `deposit` decimal(10,2) NOT NULL COMMENT '押金（元）',
  `nursing_fee` decimal(10,2) NOT NULL COMMENT '护理费用（元/月）',
  `bed_fee` decimal(10,2) NOT NULL COMMENT '床位费用（元/月）',
  `insurance_payment` decimal(10,2) NOT NULL COMMENT '医保支付（元/月）',
  `government_subsidy` decimal(10,2) NOT NULL COMMENT '政府补贴（元/月）',
  `other_fees` decimal(10,2) NOT NULL COMMENT '其他费用（元/月）',
  `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序编号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

####################
##  check_in_config data
####################

####################
##  check_in_config data
####################

####################
##  contract
####################
DROP TABLE IF EXISTS `contract`;

####################
##  table contract ddl
####################
CREATE TABLE `contract` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `elder_id` int NOT NULL COMMENT '老人ID',
  `contract_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合同名称',
  `contract_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合同编号',
  `agreement_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '协议地址（文件路径或URL）',
  `third_party_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '丙方手机号',
  `third_party_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '丙方姓名',
  `elder_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '老人姓名',
  `start_date` date NOT NULL COMMENT '开始时间',
  `end_date` date NOT NULL COMMENT '结束时间',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 (0: 未生效, 1: 已生效, 2: 已过期, 3: 已失效)',
  `sign_date` date NOT NULL COMMENT '签约日期',
  `termination_submitter` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '解除提交人',
  `termination_date` date DEFAULT NULL COMMENT '解除日期',
  `termination_agreement_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '解除协议地址（文件路径或URL）',
  `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序编号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

####################
##  contract data
####################

####################
##  contract data
####################

####################
##  elder
####################
DROP TABLE IF EXISTS `elder`;

####################
##  table elder ddl
####################
CREATE TABLE `elder` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片',
  `id_card_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '身份证号',
  `sex` int DEFAULT NULL COMMENT '性别（0:女  1:男）',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态（0：禁用，1:启用  2:请假 3:退住中 4入住中 5已退住）',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `birthday` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '出生日期',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '家庭住址',
  `id_card_national_emblem_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '身份证国徽面',
  `id_card_portrait_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '身份证人像面',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `bed_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '床位编号',
  `bed_id` bigint DEFAULT NULL COMMENT '床位id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name_id_card_no` (`name`,`id_card_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='老人表';

####################
##  elder data
####################

####################
##  elder data
####################

####################
##  family_member
####################
DROP TABLE IF EXISTS `family_member`;

####################
##  table family_member ddl
####################
CREATE TABLE `family_member` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'OpenID',
  `gender` int DEFAULT NULL COMMENT '性别(0:男，1:女)',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='老人家属';

####################
##  family_member data
####################

####################
##  family_member data
####################

####################
##  family_member_elder
####################
DROP TABLE IF EXISTS `family_member_elder`;

####################
##  table family_member_elder ddl
####################
CREATE TABLE `family_member_elder` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `family_member_id` bigint DEFAULT NULL COMMENT '家属id',
  `elder_id` bigint DEFAULT NULL COMMENT '老人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `member_id_elder_id` (`family_member_id`,`elder_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='客户老人关联表';

####################
##  family_member_elder data
####################

####################
##  family_member_elder data
####################

####################
##  floor
####################
DROP TABLE IF EXISTS `floor`;

####################
##  table floor ddl
####################
CREATE TABLE `floor` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `code` bigint DEFAULT NULL COMMENT '编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=413 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='楼层表';

####################
##  floor data
####################

####################
##  floor data
####################

####################
##  gen_table
####################
DROP TABLE IF EXISTS `gen_table`;

####################
##  table gen_table ddl
####################
CREATE TABLE `gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='代码生成业务表';

####################
##  gen_table data
####################

####################
##  gen_table data
####################

####################
##  gen_table_column
####################
DROP TABLE IF EXISTS `gen_table_column`;

####################
##  table gen_table_column ddl
####################
CREATE TABLE `gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=494 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='代码生成业务表字段';

####################
##  gen_table_column data
####################

####################
##  gen_table_column data
####################

####################
##  health_assessment
####################
DROP TABLE IF EXISTS `health_assessment`;

####################
##  table health_assessment ddl
####################
CREATE TABLE `health_assessment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `elder_name` varchar(255) DEFAULT NULL COMMENT '老人姓名',
  `id_card` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `health_score` varchar(255) DEFAULT NULL COMMENT '健康评分',
  `suggestion_for_admission` int DEFAULT NULL COMMENT '是否建议入住(0:建议，1:不建议)',
  `nursing_level_name` varchar(255) DEFAULT NULL COMMENT '推荐护理等级',
  `admission_status` int DEFAULT NULL COMMENT '入住情况(0:已入住，1:未入住)',
  `total_check_date` varchar(64) DEFAULT NULL COMMENT '总检日期',
  `assessment_time` datetime DEFAULT NULL COMMENT '评估时间',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='健康评估表';

####################
##  health_assessment data
####################

####################
##  health_assessment data
####################

####################
##  health_assessment_detail
####################
DROP TABLE IF EXISTS `health_assessment_detail`;

####################
##  table health_assessment_detail ddl
####################
CREATE TABLE `health_assessment_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `health_assessment_id` bigint NOT NULL COMMENT '健康评估id',
  `birth_date` datetime DEFAULT NULL COMMENT '出生日期',
  `age` int DEFAULT NULL COMMENT '年龄',
  `gender` int DEFAULT NULL COMMENT '性别(0:男，1:女)',
  `risk_level` varchar(255) DEFAULT NULL COMMENT '严重危险(健康, 提示, 风险, 危险, 严重危险)',
  `physical_exam_institution` varchar(255) DEFAULT NULL COMMENT '体检机构',
  `physical_report_url` varchar(255) DEFAULT NULL COMMENT '体检报告URL链接',
  `report_summary` text COMMENT '报告总结',
  `abnormal_analysis` text COMMENT '异常分析',
  `system_score` varchar(255) DEFAULT NULL COMMENT '健康系统分值',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='健康评估详情表';

####################
##  health_assessment_detail data
####################

####################
##  health_assessment_detail data
####################

####################
##  nursing_elder
####################
DROP TABLE IF EXISTS `nursing_elder`;

####################
##  table nursing_elder ddl
####################
CREATE TABLE `nursing_elder` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nursing_id` bigint DEFAULT NULL COMMENT '护理员id',
  `elder_id` bigint DEFAULT NULL COMMENT '老人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `nursing_id` (`nursing_id`,`elder_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='护理员老人关联表';

####################
##  nursing_elder data
####################

####################
##  nursing_elder data
####################

####################
##  nursing_level
####################
DROP TABLE IF EXISTS `nursing_level`;

####################
##  table nursing_level ddl
####################
CREATE TABLE `nursing_level` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '等级名称',
  `plan_id` int NOT NULL COMMENT '护理计划ID',
  `fee` decimal(10,2) NOT NULL COMMENT '护理费用',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（0：禁用，1：启用）',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '等级说明',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='护理等级表';

####################
##  nursing_level data
####################

####################
##  nursing_level data
####################

####################
##  nursing_plan
####################
DROP TABLE IF EXISTS `nursing_plan`;

####################
##  table nursing_plan ddl
####################
CREATE TABLE `nursing_plan` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `sort_no` int DEFAULT NULL COMMENT '排序号',
  `plan_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '名称',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 0禁用 1启用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `plan_name` (`plan_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='护理计划表';

####################
##  nursing_plan data
####################

####################
##  nursing_plan data
####################

####################
##  nursing_project
####################
DROP TABLE IF EXISTS `nursing_project`;

####################
##  table nursing_project ddl
####################
CREATE TABLE `nursing_project` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `order_no` int DEFAULT NULL COMMENT '排序号',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '单位',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片',
  `nursing_requirement` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '护理要求',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态（0：禁用，1：启用）',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='护理项目表';

####################
##  nursing_project data
####################

####################
##  nursing_project data
####################

####################
##  nursing_project_plan
####################
DROP TABLE IF EXISTS `nursing_project_plan`;

####################
##  table nursing_project_plan ddl
####################
CREATE TABLE `nursing_project_plan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `plan_id` int NOT NULL COMMENT '计划id',
  `project_id` int NOT NULL COMMENT '项目id',
  `execute_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划执行时间',
  `execute_cycle` int NOT NULL COMMENT '执行周期 0 天 1 周 2月',
  `execute_frequency` int NOT NULL COMMENT '执行频次',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1747 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='护理计划和项目关联表';

####################
##  nursing_project_plan data
####################

####################
##  nursing_project_plan data
####################

####################
##  nursing_task
####################
DROP TABLE IF EXISTS `nursing_task`;

####################
##  table nursing_task ddl
####################
CREATE TABLE `nursing_task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nursing_id` varchar(50) DEFAULT NULL COMMENT '护理员id',
  `project_id` int NOT NULL COMMENT '项目id',
  `project_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '护理项目名称',
  `elder_id` bigint DEFAULT NULL COMMENT '老人id',
  `elder_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '老人姓名',
  `bed_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '床位编号',
  `estimated_server_time` datetime DEFAULT NULL COMMENT '预计服务时间',
  `real_server_time` datetime DEFAULT NULL COMMENT '实际服务时间',
  `mark` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行记录',
  `cancel_reason` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '取消原因',
  `status` int DEFAULT NULL COMMENT '状态  1待执行 2已执行 3已关闭 ',
  `task_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='护理任务表';

####################
##  nursing_task data
####################

####################
##  nursing_task data
####################

####################
##  reservation
####################
DROP TABLE IF EXISTS `reservation`;

####################
##  table reservation ddl
####################
CREATE TABLE `reservation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '预约人姓名',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '预约人手机号',
  `time` datetime NOT NULL COMMENT '预约时间',
  `visitor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '探访人',
  `type` int NOT NULL COMMENT '预约类型，0：参观预约，1：探访预约',
  `status` int NOT NULL COMMENT '预约状态，0：待报道，1：已完成，2：取消，3：过期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name_mobile_time_visitor` (`mobile`,`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='预约信息表';

####################
##  reservation data
####################

####################
##  reservation data
####################

####################
##  room
####################
DROP TABLE IF EXISTS `room`;

####################
##  table room ddl
####################
CREATE TABLE `room` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '房间编号',
  `sort` int DEFAULT NULL COMMENT '排序号',
  `room_type_id` bigint DEFAULT NULL COMMENT '房间类型id',
  `floor_id` bigint DEFAULT NULL COMMENT '楼层id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='房间表';

####################
##  room data
####################

####################
##  room data
####################

####################
##  room_type
####################
DROP TABLE IF EXISTS `room_type`;

####################
##  table room_type ddl
####################
CREATE TABLE `room_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '房型名称',
  `bed_count` int NOT NULL DEFAULT '0' COMMENT '床位数量',
  `price` decimal(10,2) NOT NULL COMMENT '床位费用',
  `introduction` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '介绍',
  `photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '照片',
  `status` tinyint NOT NULL COMMENT '状态，0：禁用，1：启用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '更新人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='房型表';

####################
##  room_type data
####################

####################
##  room_type data
####################

####################
##  student
####################
DROP TABLE IF EXISTS `student`;

####################
##  table student ddl
####################
CREATE TABLE `student` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生';

####################
##  student data
####################

####################
##  student data
####################

####################
##  sys_config
####################
DROP TABLE IF EXISTS `sys_config`;

####################
##  table sys_config ddl
####################
CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='参数配置表';

####################
##  sys_config data
####################

####################
##  sys_config data
####################

####################
##  sys_dept
####################
DROP TABLE IF EXISTS `sys_dept`;

####################
##  table sys_dept ddl
####################
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='部门表';

####################
##  sys_dept data
####################

####################
##  sys_dept data
####################

####################
##  sys_dict_data
####################
DROP TABLE IF EXISTS `sys_dict_data`;

####################
##  table sys_dict_data ddl
####################
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

####################
##  sys_dict_data data
####################

####################
##  sys_dict_data data
####################

####################
##  sys_dict_type
####################
DROP TABLE IF EXISTS `sys_dict_type`;

####################
##  table sys_dict_type ddl
####################
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

####################
##  sys_dict_type data
####################

####################
##  sys_dict_type data
####################

####################
##  sys_job
####################
DROP TABLE IF EXISTS `sys_job`;

####################
##  table sys_job ddl
####################
CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='定时任务调度表';

####################
##  sys_job data
####################

####################
##  sys_job data
####################

####################
##  sys_job_log
####################
DROP TABLE IF EXISTS `sys_job_log`;

####################
##  table sys_job_log ddl
####################
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='定时任务调度日志表';

####################
##  sys_job_log data
####################

####################
##  sys_job_log data
####################
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at com.zzyl.quartz.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:61)
	at com.zzyl.quartz.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:33)
	at com.zzyl.quartz.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)
	at com.zzyl.quartz.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:43)
	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)
	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)
Caused by: org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.PersistenceException: 
### Error updating database.  Cause: com.zzyl.common.exception.ServiceException: 获取用户信息异常
### The error may exist in com/zzyl/nursing/mapper/ContractMapper.java (best guess)
### The error may involve com.zzyl.nursing.mapper.ContractMapper.updateById
### The error occurred while executing an update
### Cause: com.zzyl.common.exception.ServiceException: 获取用户信息异常
	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:97)
	at com.baomidou.mybatisplus.extension.toolkit.SqlHelper.executeBatch(SqlHelper.java:192)
	at com.baomidou.mybatisplus.extension.toolkit.SqlHelper.executeBatch(SqlHelper.java:217)
	at com.baomidou.mybatisplus.extension.service.impl.ServiceImpl.executeBatch(ServiceImpl.java:240)
	at com.baomidou.mybatisplus.extension.service.impl.ServiceImpl.updateBatchById(ServiceImpl.java:192)
	at com.baomidou.mybatisplus.extension.service.IService.updateBatchById(IService.java:268)
	at com.zzyl.nursing.','批量合同状态更新任务','2025-04-21T15:36:06','DEFAULT',65,'contractJob.updateContractStatus','1');
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:874)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1359)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:310)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:209)
	at com.zzyl.common.utils.spring.SpringUtils.getBean(SpringUtils.java:49)
	at com.zzyl.quartz.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:32)
	at com.zzyl.quartz.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)
	at com.zzyl.quartz.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:43)
	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)
	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)
','测试任务','2025-04-22T00:30:47','DEFAULT',81,'helloJob.myJob','1');
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:874)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1359)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:310)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:209)
	at com.zzyl.common.utils.spring.SpringUtils.getBean(SpringUtils.java:49)
	at com.zzyl.quartz.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:32)
	at com.zzyl.quartz.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)
	at com.zzyl.quartz.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:43)
	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)
	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)
','测试任务','2025-04-22T00:30:57','DEFAULT',82,'helloJob.myJob','1');
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:874)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1359)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:310)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:209)
	at com.zzyl.common.utils.spring.SpringUtils.getBean(SpringUtils.java:49)
	at com.zzyl.quartz.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:32)
	at com.zzyl.quartz.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)
	at com.zzyl.quartz.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:43)
	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)
	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)
','测试任务','2025-04-22T00:31:07','DEFAULT',83,'helloJob.myJob','1');
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:874)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1359)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:310)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:209)
	at com.zzyl.common.utils.spring.SpringUtils.getBean(SpringUtils.java:49)
	at com.zzyl.quartz.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:32)
	at com.zzyl.quartz.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)
	at com.zzyl.quartz.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:43)
	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)
	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)
','测试任务','2025-04-22T00:31:17','DEFAULT',84,'helloJob.myJob','1');
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:874)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1359)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:310)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:209)
	at com.zzyl.common.utils.spring.SpringUtils.getBean(SpringUtils.java:49)
	at com.zzyl.quartz.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:32)
	at com.zzyl.quartz.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)
	at com.zzyl.quartz.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:43)
	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)
	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)
','测试任务','2025-04-22T00:31:27','DEFAULT',85,'helloJob.myJob','1');
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at com.zzyl.quartz.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:61)
	at com.zzyl.quartz.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:33)
	at com.zzyl.quartz.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)
	at com.zzyl.quartz.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:43)
	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)
	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)
Caused by: org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.PersistenceException: 
### Error updating database.  Cause: com.zzyl.common.exception.ServiceException: 获取用户信息异常
### The error may exist in com/zzyl/nursing/mapper/ContractMapper.java (best guess)
### The error may involve com.zzyl.nursing.mapper.ContractMapper.updateById
### The error occurred while executing an update
### Cause: com.zzyl.common.exception.ServiceException: 获取用户信息异常
	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:97)
	at com.baomidou.mybatisplus.extension.toolkit.SqlHelper.executeBatch(SqlHelper.java:192)
	at com.baomidou.mybatisplus.extension.toolkit.SqlHelper.executeBatch(SqlHelper.java:217)
	at com.baomidou.mybatisplus.extension.service.impl.ServiceImpl.executeBatch(ServiceImpl.java:240)
	at com.baomidou.mybatisplus.extension.service.impl.ServiceImpl.updateBatchById(ServiceImpl.java:192)
	at com.baomidou.mybatisplus.extension.service.IService.updateBatchById(IService.java:268)
	at com.zzyl.nursing.','合同定时批量更新任务','2025-04-22T02:10:50','DEFAULT',117,'contractJob.updateContractStatusJob','1');
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:874)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1359)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:310)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:209)
	at com.zzyl.common.utils.spring.SpringUtils.getBean(SpringUtils.java:49)
	at com.zzyl.quartz.util.JobInvokeUtil.invokeMethod(JobInvokeUtil.java:32)
	at com.zzyl.quartz.util.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:19)
	at com.zzyl.quartz.util.AbstractQuartzJob.execute(AbstractQuartzJob.java:43)
	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)
	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)
','护理任务生成定时任务','2025-08-26T07:36:13','DEFAULT',124,'createNursingTaskJob.createNursingTaskJob','1');

####################
##  sys_logininfor
####################
DROP TABLE IF EXISTS `sys_logininfor`;

####################
##  table sys_logininfor ddl
####################
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  KEY `idx_sys_logininfor_s` (`status`) USING BTREE,
  KEY `idx_sys_logininfor_lt` (`login_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=523 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统访问记录';

####################
##  sys_logininfor data
####################

####################
##  sys_logininfor data
####################

####################
##  sys_menu
####################
DROP TABLE IF EXISTS `sys_menu`;

####################
##  table sys_menu ddl
####################
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由名称',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2055 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

####################
##  sys_menu data
####################

####################
##  sys_menu data
####################

####################
##  sys_notice
####################
DROP TABLE IF EXISTS `sys_notice`;

####################
##  table sys_notice ddl
####################
CREATE TABLE `sys_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='通知公告表';

####################
##  sys_notice data
####################

####################
##  sys_notice data
####################

####################
##  sys_oper_log
####################
DROP TABLE IF EXISTS `sys_oper_log`;

####################
##  table sys_oper_log ddl
####################
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '模块标题',
  `business_type` int DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求方式',
  `operator_type` int DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '返回参数',
  `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint DEFAULT '0' COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  KEY `idx_sys_oper_log_bt` (`business_type`) USING BTREE,
  KEY `idx_sys_oper_log_s` (`status`) USING BTREE,
  KEY `idx_sys_oper_log_ot` (`oper_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=672 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';

####################
##  sys_oper_log data
####################

####################
##  sys_oper_log data
####################
### Error updating database.  Cause: java.sql.SQLException: Field \'create_time\' doesn\'t have a default value
### The error may exist in file [E:\\code\\2024\\zzyl-ruoyi-ai-code\\day02\\zzyl\\zzyl-nursing-platform\\target\\classes\\mapper\\nursing\\NursingPlanMapper.xml]
### The error may involve com.zzyl.nursing.mapper.NursingPlanMapper.insertNursingPlan-Inline
### The error occurred while setting parameters
### SQL: insert into nursing_plan          ( sort_no,             plan_name,             status )           values ( ?,             ?,             ? )
### Cause: java.sql.SQLException: Field \'create_time\' doesn\'t have a default value
; Field \'create_time\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'create_time\' doesn\'t have a default value','com.zzyl.nursing.controller.NursingPlanController.add()','{`planName`:`测试护理计划1`,`projectPlans`:[{`executeCycle`:0,`executeFrequency`:1,`executeTime`:`17:11:40`,`params`:{},`projectId`:86},{`executeCycle`:1,`executeFrequency`:1,`executeTime`:`17:11:52`,`params`:{},`projectId`:87}],`sortNo`:1,`status`:1}','admin','研发部门','POST','护理计划','内网IP',1,'127.0.0.1','',57,1,'/nursing/plan',136,1);
### Error updating database.  Cause: java.sql.SQLException: Field \'create_time\' doesn\'t have a default value
### The error may exist in file [E:\\code\\2024\\zzyl-ruoyi-ai-code\\day02\\zzyl\\zzyl-nursing-platform\\target\\classes\\mapper\\nursing\\NursingPlanMapper.xml]
### The error may involve com.zzyl.nursing.mapper.NursingPlanMapper.insertNursingPlan-Inline
### The error occurred while setting parameters
### SQL: insert into nursing_plan          ( sort_no,             plan_name,             status )           values ( ?,             ?,             ? )
### Cause: java.sql.SQLException: Field \'create_time\' doesn\'t have a default value
; Field \'create_time\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'create_time\' doesn\'t have a default value','com.zzyl.nursing.controller.NursingPlanController.add()','{`planName`:`测试护理计划1`,`projectPlans`:[{`executeCycle`:0,`executeFrequency`:1,`executeTime`:`17:11:40`,`params`:{},`projectId`:86},{`executeCycle`:1,`executeFrequency`:1,`executeTime`:`17:11:52`,`params`:{},`projectId`:87}],`sortNo`:1,`status`:1}','admin','研发部门','POST','护理计划','内网IP',1,'127.0.0.1','',7,1,'/nursing/plan',137,1);
### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Column \'create_time\' cannot be null
### The error may exist in file [E:\\code\\2024\\zzyl-ruoyi-ai-code\\day02\\zzyl\\zzyl-nursing-platform\\target\\classes\\mapper\\nursing\\NursingProjectPlanMapper.xml]
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: INSERT INTO nursing_project_plan (plan_id, project_id, execute_time, execute_cycle, execute_frequency,         create_by, update_by, create_time, update_time)         VALUES                        (?, ?, ?, ?, ?,             ?, ?, ?, ?)          ,              (?, ?, ?, ?, ?,             ?, ?, ?, ?)
### Cause: java.sql.SQLIntegrityConstraintViolationException: Column \'create_time\' cannot be null
; Column \'create_time\' cannot be null; nested exception is java.sql.SQLIntegrityConstraintViolationException: Column \'create_time\' cannot be null','com.zzyl.nursing.controller.NursingPlanController.add()','{`planName`:`测试护理计划1`,`projectPlans`:[{`executeCycle`:0,`executeFrequency`:1,`executeTime`:`17:11:40`,`params`:{},`projectId`:86},{`executeCycle`:1,`executeFrequency`:1,`executeTime`:`17:11:52`,`params`:{},`projectId`:87}],`sortNo`:1,`status`:1}','admin','研发部门','POST','护理计划','内网IP',1,'127.0.0.1','',303,1,'/nursing/plan',138,1);
### Error updating database.  Cause: java.sql.SQLException: Field \'fee\' doesn\'t have a default value
### The error may exist in file [E:\\code\\2024\\zzyl-ruoyi-ai-code\\day02\\zzyl\\zzyl-nursing-platform\\target\\classes\\mapper\\nursing\\NursingLevelMapper.xml]
### The error may involve com.zzyl.nursing.mapper.NursingLevelMapper.insertNursingLevel-Inline
### The error occurred while setting parameters
### SQL: insert into nursing_level          ( name,             lplan_id,                          status,             description,             create_time )           values ( ?,             ?,                          ?,             ?,             ? )
### Cause: java.sql.SQLException: Field \'fee\' doesn\'t have a default value
; Field \'fee\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'fee\' doesn\'t have a default value','com.zzyl.nursing.controller.NursingLevelController.add()','{`createTime`:`2024-08-20 11:18:08`,`description`:`1223`,`lplanId`:133,`name`:`1号护理计划`,`params`:{},`status`:1}','admin','研发部门','POST','护理等级','内网IP',1,'127.0.0.1','',58,1,'/nursing/level',178,1);
### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
### The error may exist in com/zzyl/nursing/mapper/NursingProjectMapper.java (best guess)
### The error may involve com.zzyl.nursing.mapper.NursingProjectMapper.insert-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO nursing_project  ( name, order_no, unit, price, image, nursing_requirement, status, create_by, create_time )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ? )
### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
; Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'','com.zzyl.nursing.controller.NursingProjectController.add()','{`createBy`:`1`,`createTime`:`2025-04-24 16:35:06`,`image`:`https://itheim.oss-cn-beijing.aliyuncs.com/c397ebb5-2dd0-4257-938e-779a5077e290.png`,`name`:`修剪指甲`,`nursingRequirement`:`2222`,`orderNo`:1,`params`:{},`price`:1,`status`:1,`unit`:`1`}','admin','研发部门','POST','护理项目','内网IP',1,'192.168.100.1','',155,1,'/nursing/project',469,1);
### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
### The error may exist in com/zzyl/nursing/mapper/NursingProjectMapper.java (best guess)
### The error may involve com.zzyl.nursing.mapper.NursingProjectMapper.insert-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO nursing_project  ( name, order_no, unit, price, image, nursing_requirement, status, create_by, create_time )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ? )
### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
; Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'','com.zzyl.nursing.controller.NursingProjectController.add()','{`createBy`:`1`,`createTime`:`2025-04-24 16:35:07`,`image`:`https://itheim.oss-cn-beijing.aliyuncs.com/c397ebb5-2dd0-4257-938e-779a5077e290.png`,`name`:`修剪指甲`,`nursingRequirement`:`2222`,`orderNo`:1,`params`:{},`price`:1,`status`:1,`unit`:`1`}','admin','研发部门','POST','护理项目','内网IP',1,'192.168.100.1','',12,1,'/nursing/project',470,1);
### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
### The error may exist in com/zzyl/nursing/mapper/NursingProjectMapper.java (best guess)
### The error may involve com.zzyl.nursing.mapper.NursingProjectMapper.insert-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO nursing_project  ( name, order_no, unit, price, image, nursing_requirement, status, create_by, create_time )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ? )
### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
; Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'','com.zzyl.nursing.controller.NursingProjectController.add()','{`createBy`:`1`,`createTime`:`2025-04-24 16:42:25`,`image`:`https://itheim.oss-cn-beijing.aliyuncs.com/7d293ef4-7c09-48df-a0e9-c10c1cacea04.png`,`name`:`修剪指甲`,`nursingRequirement`:`111`,`orderNo`:1,`params`:{},`price`:1,`status`:1,`unit`:`1`}','admin','研发部门','POST','护理项目','内网IP',1,'127.0.0.1','',67,1,'/nursing/project',471,1);
### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
### The error may exist in com/zzyl/nursing/mapper/NursingProjectMapper.java (best guess)
### The error may involve com.zzyl.nursing.mapper.NursingProjectMapper.insert-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO nursing_project  ( name, order_no, unit, price, image, nursing_requirement, status, create_by, create_time )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ? )
### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
; Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'','com.zzyl.nursing.controller.NursingProjectController.add()','{`createBy`:`1`,`createTime`:`2025-04-24 16:42:26`,`image`:`https://itheim.oss-cn-beijing.aliyuncs.com/7d293ef4-7c09-48df-a0e9-c10c1cacea04.png`,`name`:`修剪指甲`,`nursingRequirement`:`111`,`orderNo`:1,`params`:{},`price`:1,`status`:1,`unit`:`1`}','admin','研发部门','POST','护理项目','内网IP',1,'127.0.0.1','',12,1,'/nursing/project',472,1);
### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
### The error may exist in com/zzyl/nursing/mapper/NursingProjectMapper.java (best guess)
### The error may involve com.zzyl.nursing.mapper.NursingProjectMapper.insert-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO nursing_project  ( name, order_no, unit, price, image, nursing_requirement, status, create_by, create_time )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ? )
### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
; Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'','com.zzyl.nursing.controller.NursingProjectController.add()','{`createBy`:`1`,`createTime`:`2025-04-25 16:02:55`,`image`:`https://itheim.oss-cn-beijing.aliyuncs.com/977454b1-98d2-4047-8cf9-d55c88387258.png`,`name`:`修剪指甲`,`nursingRequirement`:`1111`,`orderNo`:1,`params`:{},`price`:1,`status`:1,`unit`:`11`}','admin','研发部门','POST','护理项目','内网IP',1,'127.0.0.1','',133,1,'/nursing/project',477,1);
### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
### The error may exist in com/zzyl/nursing/mapper/NursingProjectMapper.java (best guess)
### The error may involve com.zzyl.nursing.mapper.NursingProjectMapper.insert-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO nursing_project  ( name, order_no, unit, price, image, nursing_requirement, status, create_by, create_time )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ? )
### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
; Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'','com.zzyl.nursing.controller.NursingProjectController.add()','{`createBy`:`1`,`createTime`:`2025-04-25 16:02:57`,`image`:`https://itheim.oss-cn-beijing.aliyuncs.com/977454b1-98d2-4047-8cf9-d55c88387258.png`,`name`:`修剪指甲`,`nursingRequirement`:`1111`,`orderNo`:1,`params`:{},`price`:1,`status`:1,`unit`:`11`}','admin','研发部门','POST','护理项目','内网IP',1,'127.0.0.1','',10,1,'/nursing/project',478,1);
### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
### The error may exist in com/zzyl/nursing/mapper/NursingProjectMapper.java (best guess)
### The error may involve com.zzyl.nursing.mapper.NursingProjectMapper.insert-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO nursing_project  ( name, order_no, unit, price, image, nursing_requirement, status, create_by, create_time )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ? )
### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'
; Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'修剪指甲\' for key \'nursing_project.name\'','com.zzyl.nursing.controller.NursingProjectController.add()','{`createBy`:`1`,`createTime`:`2025-04-25 16:03:00`,`image`:`https://itheim.oss-cn-beijing.aliyuncs.com/977454b1-98d2-4047-8cf9-d55c88387258.png`,`name`:`修剪指甲`,`nursingRequirement`:`1111`,`orderNo`:1,`params`:{},`price`:1,`status`:1,`unit`:`11`}','admin','研发部门','POST','护理项目','内网IP',1,'127.0.0.1','',11,1,'/nursing/project',479,1);

####################
##  sys_post
####################
DROP TABLE IF EXISTS `sys_post`;

####################
##  table sys_post ddl
####################
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='岗位信息表';

####################
##  sys_post data
####################

####################
##  sys_post data
####################

####################
##  sys_role
####################
DROP TABLE IF EXISTS `sys_role`;

####################
##  table sys_role ddl
####################
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色信息表';

####################
##  sys_role data
####################

####################
##  sys_role data
####################

####################
##  sys_role_dept
####################
DROP TABLE IF EXISTS `sys_role_dept`;

####################
##  table sys_role_dept ddl
####################
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和部门关联表';

####################
##  sys_role_dept data
####################

####################
##  sys_role_dept data
####################

####################
##  sys_role_menu
####################
DROP TABLE IF EXISTS `sys_role_menu`;

####################
##  table sys_role_menu ddl
####################
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';

####################
##  sys_role_menu data
####################

####################
##  sys_role_menu data
####################

####################
##  sys_user
####################
DROP TABLE IF EXISTS `sys_user`;

####################
##  table sys_user ddl
####################
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

####################
##  sys_user data
####################

####################
##  sys_user data
####################

####################
##  sys_user_post
####################
DROP TABLE IF EXISTS `sys_user_post`;

####################
##  table sys_user_post ddl
####################
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户与岗位关联表';

####################
##  sys_user_post data
####################

####################
##  sys_user_post data
####################

####################
##  sys_user_role
####################
DROP TABLE IF EXISTS `sys_user_role`;

####################
##  table sys_user_role ddl
####################
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';

####################
##  sys_user_role data
####################

####################
##  sys_user_role data
####################