var Common = {
	// 对于单个字段进行验证,必须以字段 id+"Msg"的label显示出结果
	rValidation : function(id, action) {
		dojo.connect(dojo.byId(id), 'onblur', null, function() {
					// 如果已经有错误，则不进行ajax校验
					console.debug(id);
					if (dojo.attr(id + "Msg", 'innerHTML') != "") {
						return;
					}
					var name = dojo.attr(id, 'name');
					dojo.xhrPost({
								url : action,
								content : {
									name : (dojo.attr(id,'value'))
								},
								handleAs : 'json',
								load : function(response, ioArgs) {
									if (response.validate != "true") {
										dojo.attr(id + "Msg", 'innerHTML', response);
									} else {
										dojo.attr(id + "Msg", 'innerHTML', '');
									}
								}
							});
				});
	},
	// 对于单个字段进行前台的正则表达式验证
	bValidation : function(id) {
		dojo.connect(dojo.byId(id), 'onkeyup', null, function() {
			// 首先清空报错信息
			dojo.attr(id + "Msg",'html','');
			// 获取当前输入项的正则表达式，使用正则表达式进行验证
			var pattern = dojo.attr(id,'regExp');
			if (new RegExp(pattern).exec(dojo.attr(id,'value')) != null) {
				dojo.attr(id + 'Msg','innerHTML','');
			} else {
				dojo.attr(id + 'Msg','innerHTML',dojo.attr(id,'invalidMessage'));
			}
		});
	},
	// 对于整个表单的验证,注册表单所有input
	formValidation : function(id) {
		var inputs = dojo.query("#" + id + " input");
		for (var i = 0; i < inputs.length; i++) {
			if(dojo.attr(inputs[i],"type")=="submit"){
				continue;
			}
			if (typeof(dojo.attr(inputs[i],"regExp")) != null) {
				Common.bValidation(dojo.attr(inputs[i],"id"));
			}
			alert(dojo.attr(inputs[i],'remote'));
			if(typeof(dojo.attr(inputs[i],'remote')) != null){
				Common.rValidation(dojo.attr(inputs[i],"id"),dojo.attr(inputs[i],'url'));
			}
		}
	},
	//对于表单提交的验证，检查所有表单下面的label[class="error_label"]的部分，如果有一个不为“”，则return false
	formSubmit:function(id){
		dojo.connect(dojo.byId(id),'onsubmit',null,function(){
		    var error_labels = dojo.query(".error_label");
		    for(var i=0;i<error_labels.length;i++){
		    	if(dojo.attr(error_labels[i], 'innerHTML') != ""){
		    		return "return false";
		    	}
		    }
		    return true;
		});
	}

}