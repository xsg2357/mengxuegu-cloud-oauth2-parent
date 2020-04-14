
-- ----------------------------
-- Table structure for sys_permission 
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限 ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限 ID (0为顶级菜单)',
  `name` varchar(64) NOT NULL COMMENT '权限名称',
  `code` varchar(64) DEFAULT NULL COMMENT '授权标识符',
  `url` varchar(255) DEFAULT NULL COMMENT '授权路径',
  `type` int(2) NOT NULL DEFAULT '1' COMMENT '类型(1菜单，2按钮)',
  `icon` varchar(200) DEFAULT NULL COMMENT '图标',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('11', '0', '首页', 'sys:index', '/', '1', 'fa fa-dashboard', '', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('17', '0', '系统管理', 'sys:manage', null, '1', 'fa fa-cogs', null, '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('18', '17', '用户管理', 'sys:user', '/user', '1', 'fa fa-users', null, '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('19', '18', '列表', 'sys:user:list', '', '2', '', '员工列表', '2023-08-08 11:11:11', '2023-08-08 11:11:11');
INSERT INTO `sys_permission` VALUES ('20', '18', '新增', 'sys:user:add', '', '2', '', '新增用户', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('21', '18', '修改', 'sys:user:edit', '', '2', '', '修改用户', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('22', '18', '删除', 'sys:user:delete', '', '2', '', '删除用户', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('23', '17', '角色管理', 'sys:role', '/role', '1', 'fa fa-user-secret', null, '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('24', '23', '列表', 'sys:role:list', null, '2', null, '角色列表', '2023-08-08 11:11:11', '2023-08-08 11:11:11');
INSERT INTO `sys_permission` VALUES ('25', '23', '新增', 'sys:role:add', '', '2', '', '新增角色', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('26', '23', '修改', 'sys:role:edit', '', '2', '', '修改角色', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('27', '23', '删除', 'sys:role:delete', '', '2', '', '删除角色', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('28', '17', '权限管理', 'sys:permission', '/permission', '1', 'fa fa-cog', null, '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('29', '28', '列表', 'sys:permission:list', null, '2', null, '权限列表', '2023-08-08 11:11:11', '2023-08-08 11:11:11');
INSERT INTO `sys_permission` VALUES ('30', '28', '新增', 'sys:permission:add', '', '2', null, '新增权限', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('31', '28', '修改', 'sys:permission:edit', '', '2', null, '修改权限', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES ('32', '28', '删除', 'sys:permission:delete', '', '2', '', '删除权限', '2023-08-08 11:11:11', '2023-08-09 15:26:28');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色 ID',
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '角色说明',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('9', '超级管理员', '拥有所有的权限', '2023-08-08 11:11:11', '2023-08-08 11:11:11');
INSERT INTO `sys_role` VALUES ('10', '普通管理员', '拥有查看权限', '2023-08-08 11:11:11', '2023-08-08 11:11:11');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '9', '11');
INSERT INTO `sys_role_permission` VALUES ('2', '9', '17');
INSERT INTO `sys_role_permission` VALUES ('3', '9', '18');
INSERT INTO `sys_role_permission` VALUES ('4', '9', '19');
INSERT INTO `sys_role_permission` VALUES ('5', '9', '20');
INSERT INTO `sys_role_permission` VALUES ('6', '9', '21');
INSERT INTO `sys_role_permission` VALUES ('7', '9', '22');
INSERT INTO `sys_role_permission` VALUES ('8', '9', '23');
INSERT INTO `sys_role_permission` VALUES ('9', '9', '24');
INSERT INTO `sys_role_permission` VALUES ('10', '9', '25');
INSERT INTO `sys_role_permission` VALUES ('11', '9', '26');
INSERT INTO `sys_role_permission` VALUES ('12', '9', '27');
INSERT INTO `sys_role_permission` VALUES ('13', '9', '28');
INSERT INTO `sys_role_permission` VALUES ('14', '9', '29');
INSERT INTO `sys_role_permission` VALUES ('15', '9', '30');
INSERT INTO `sys_role_permission` VALUES ('16', '9', '31');
INSERT INTO `sys_role_permission` VALUES ('17', '9', '32');
INSERT INTO `sys_role_permission` VALUES ('18', '10', '11');
INSERT INTO `sys_role_permission` VALUES ('19', '10', '17');
INSERT INTO `sys_role_permission` VALUES ('20', '10', '18');
INSERT INTO `sys_role_permission` VALUES ('21', '10', '19');
INSERT INTO `sys_role_permission` VALUES ('22', '10', '23');
INSERT INTO `sys_role_permission` VALUES ('23', '10', '24');
INSERT INTO `sys_role_permission` VALUES ('24', '10', '28');
INSERT INTO `sys_role_permission` VALUES ('25', '10', '29');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户 ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码，加密存储, admin/1234',
  `is_account_non_expired` int(2) DEFAULT '1' COMMENT '帐户是否过期(1 未过期，0已过期)',
  `is_account_non_locked` int(2) DEFAULT '1' COMMENT '帐户是否被锁定(1 未过期，0已过期)',
  `is_credentials_non_expired` int(2) DEFAULT '1' COMMENT '密码是否过期(1 未过期，0已过期)',
  `is_enabled` int(2) DEFAULT '1' COMMENT '帐户是否可用(1 可用，0 删除用户)',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(20) DEFAULT NULL COMMENT '注册手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '注册邮箱',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('9', 'admin', '$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm', '1', '1', '1', '1', '梦学谷', '16888888888', 'mengxuegu888@163.com', '2023-08-08 11:11:11', '2019-12-16 10:25:53');
INSERT INTO `sys_user` VALUES ('10', 'test', '$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm', '1', '1', '1', '1', '测试', '16888886666', 'test11@qq.com', '2023-08-08 11:11:11', '2023-08-08 11:11:11');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '9', '9');
INSERT INTO `sys_user_role` VALUES ('2', '10', '10');

-- 获取id=9的用户权限信息
SELECT
	DISTINCT p.id,	p.parent_id, p.name, p.code, p.url, p.type,
	p.icon, p.remark, p.create_date, p.update_date
FROM
  sys_user AS u
  LEFT JOIN sys_user_role AS ur
	ON u.id = ur.user_id
  LEFT JOIN sys_role AS r
	ON r.id = ur.role_id
  LEFT JOIN sys_role_permission AS rp
	ON r.id = rp.role_id
  LEFT JOIN sys_permission AS p
	ON p.id = rp.permission_id
WHERE u.id = 9
ORDER BY p.id
