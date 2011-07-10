var Common = {
	// 对于单个字段进行验证,必须以字段 id+"Msg"的label显示出结果
	rValidation : function(id, action) {
		dojo.connect(dojo.byId(id), 'onblur', null, function() {
					// 如果已经有错误，则不进行ajax校验
					if (dojo.query("#" + id + "Msg").html() != "") {
						return;
					}
					dojo.xhrPost({
								url : action,
								content : {
									(dojo.byId(id).name) : (dojo.byId(id).value)
								},
								handleAs : 'json',
								load : function(response, ioArgs) {
									if (response != "OK") {
										dojo.query("#" + id + "Msg")
												.html(response);
									} else {
										dojo.query("#" + id + "Msg").html("");
									}
								}
							})
				});
	},
	// 对于单个字段进行前台的正则表达式验证
	bValidation : function(id) {
		dojo.connect(dojo.byId(id), 'onkeyup', null, function() {
			// 首先清空报错信息
			dojo.query("#" + id + "Msg").html("");
			// 获取当前输入项的正则表达式，使用正则表达式进行验证
			var pattern = dojo.byId(id).regExp;
			if (new RegExp(pattern).exec(dojo.byId(id).value) != null) {
				dojo.query('#' + id + 'Msg').html("");
			} else {
				dojo.query('#' + id + 'Msg').html(dojo.byId(id).invalidMessage);
			}

		});
	},
	// 对于整个表单的验证,注册表单所有input
	formValidation : function(id) {
		var inputs = dojo.query("#" + id + " input");
		for (var i = 0; i < inputs.length; i++) {
			if (typeof(inputs[i].regExp) != "undefined") {
				bValidation(inputs[i].id);
			}
			if(typeof(inputs[i].remote) != "undefined"){
				rValidation(id,dojo.byId(id).url);
			}
		}
	},
	//对于表单提交的验证，检查所有表单下面的label[class="error_label"]的部分，如果有一个不为“”，则return false
	formSubmit:function(id){
		dojo.connect(dojo.byId(id),'onsubmit',null,function(){
		    var error_labels = dojo.query("error_label",dojo.byId(id));
		    for(var i=0;i<error_labels.length;i++){
		    	if(error_labels[i].html()!=""){
		    		return false;
		    	}
		    }
		    return true;
		});
	}

}