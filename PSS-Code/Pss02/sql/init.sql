insert into `role` values('0','店长','店长');
insert into `role` values('1','采购员','采购员');
insert into `role` values('2','库存管理员','库存管理员');
insert into `role` values('3','销售','销售');

insert into function(function_id,url,module,title) values('0','sys/user','User','用户管理');

insert into role_privalige(role_id,function_id) values(0,0);
