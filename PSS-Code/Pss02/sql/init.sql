insert into `role` values('0','店长','店长');
insert into `role` values('1','采购员','采购员');
insert into `role` values('2','库存管理员','库存管理员');
insert into `role` values('3','销售','销售');

insert into function(function_id,url,module,title) values('0','sys/query_User','User','用户查询');
insert into function(function_id,url,module,title) values('1','sys/delete_User','User','用户删除');
insert into function(function_id,url,module,title) values('2','sys/update_User','User','用户更新');
insert into function(function_id,url,module,title) values('3','sys/add_User','User','用户增加');

insert into role_privalige(role_id,function_id) values(0,0);
insert into role_privalige(role_id,function_id) values(0,1);
insert into role_privalige(role_id,function_id) values(0,2);
insert into role_privalige(role_id,function_id) values(0,3);