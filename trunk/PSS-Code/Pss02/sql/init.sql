insert into `role` values('0','店长','店长');
insert into `role` values('1','采购员','采购员');
insert into `role` values('2','库存管理员','库存管理员');
insert into `role` values('3','销售','销售');

insert into function(function_id,url,module,title) values('0','sys/User_home','System','用户管理');
insert into function(function_id,url,module,title) values('1','purchase/Supplier_home','Purchase','供应商管理');
insert into function(function_id,url,module,title) values('2','purchase/GoodCategory_home','Purchase','货品分类管理');

insert into role_privalige(role_id,function_id) values(0,0);
insert into role_privalige(role_id,function_id) values(0,1);
insert into role_privalige(role_id,function_id) values(1,1);
insert into role_privalige(role_id,function_id) values(0,2);
insert into role_privalige(role_id,function_id) values(1,2);
