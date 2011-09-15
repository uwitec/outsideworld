var Common = {
	dialog : null,
	dialogPane : null,
	confirmDialog : null,
	confirmOK : null,
	confirmData : null,
	/* 定义一个什么都不做的空方法 */
	nullFunction : function() {

	},
	/* 在一个div下面添加一条错误信息 */
	addError : function() {

	},
	/* 用于对form表单提交前进行校验，如果有错误，则不提交，并且在出错的input上显示错误信息 */
	validateForm : function(formId) {
		var isOK = true;
		dijit.registry.filter(function(widget) {
			return widget.declaredClass == "custom.TextInput" ? true : false;
		}).forEach(function(widget) {
			if (widget.correct == "false") {
				isOK = false;
				widget.warnning();
				return;
			}
		});
		return isOK;
	},
	/* 异步提交表单，提交之前验证，有错误则不提交 */
	submitFormAsync : function(formId, success) {
		if (Common.validateForm(formId)) {
			var xhrArgs = {
				form : dojo.byId(formId),
				handleAs : "json",
				load : function(data) {
					if (!Common.checkLogin(data)) {
						return;
					} else if (!Common.hasErrors(data, formId)) {
						success(data);
					}
				}
			};
			dojo.xhrPost(xhrArgs);
		}
	},
	/* 同步提交表单 */
	submitForm : function(formId) {
		if (Common.validateForm(formId)) {
			dojo.byId(formId).submit();
		}
	},
	// Post data and handle as JSON
	post : function(url, data, success) {
		var xhrArgs = {
			url : url,
			handleAs : "json",
			content : data,
			load : function(response) {
				if (!Common.checkLogin(response)) {
					return;
				} else if (!Common.hasErrors(data, formId)) {
					success(data);
				}
			}
		};
		dojo.xhrPost(xhrArgs);
	},
	/* 将非登陆AJAX请求的返回结果定向至首页 */
	checkLogin : function(data) {
		if (data != null && data.login != null && data.login == false) {
			this.goTo("/Pss/");
			return false;
		} else {
			return true;
		}

	},
	/* 清空表单 */
	clearForm : function(formId) {
		var form = dojo.byId(formId);
		if (form == null) {
			return;
		}
		dojo.query("input:text", form).forEach(function(node, index, array) {
			node.value = "";
		});
		dojo.query("input:password", form).forEach(
				function(node, index, array) {
					node.value = "";
				});
		dojo.query("select", form).forEach(function(node, index, array) {
			node.selectedIndex = 0;
		});
		dojo.query("input:checkbox", form).forEach(
				function(node, index, array) {
					node.checked = false;
				});
	},
	/* 重定向URL */
	goTo : function(url) {
		window.location.href = url;
	},
	/* 设定主页面 */
	setMainPane : function(url) {
		dijit.byId("mainPane").set("href", url);
	},
	/* 弹出Dialog */
	showDialog : function(title, url) {
		if (!this.dialog) {
			this.dialog = dijit.byId("dialog");
		}
		if (!this.dialogPane) {
			this.dialogPane = dijit.byId("dialogPane");
		}
		this.dialog.set("title", title);
		this.dialogPane.set("href", url);
		this.dialog.show();
	},
	/* 隐藏Dialog */
	hideDialog : function() {
		if (this.dialog) {
			this.dialog.hide();
		}
		if (this.dialogPane) {
			this.dialogPane.set("content", "");
		}
	},
	/* 判断是否有错误，并且在页面上显示错误信息 */
	hasErrors : function(data, formId) {
		if (data == null || data == undefined) {
			return true;
		}

		var formErrors = dojo.byId(formId + "Errors");
		if (formErrors == null) {
			formErrors = dojo.byId("errors");
		}
		if (formErrors) {
			dojo.empty(formErrors);
		}

		var flag = false;
		if (data.errors != null) {
			if (formErrors != null) {
				for ( var field in data.errors) {
					flag = true;
					dojo.create("li", {
						"innerHTML" : data.errors[field]
					}, formErrors);
				}
			}
		}
		if (data.errorMessages != null) {
			if (formErrors != null) {
				for ( var msg in data.errorMessages) {
					flag = true;
					dojo.create("li", {
						"innerHTML" : data.errorMessages[msg]
					}, formErrors);
				}
			}
		}
		return flag;
	},
	/* 刷新表格数据和分页数据 */
	refreshDataGrid : function(tableId, url, data) {
		var grid = dijit.byId(tableId);
		if (!grid) {
			return;
		}
		if (!url) {
			url = grid.params;
		}
		var xhrArgs = {
			grid : grid,
			url : url,
			handleAs : "json",
			content : data,
			load : function(response) {
				var newStore = new dojo.data.ItemFileReadStore({
					data : response,
					urlPreventCache : true,
					clearOnClose : true
				});
				this.grid.params = url;
				this.grid.setStore(newStore);
				Common.refreshPager(response);
			}
		};
		dojo.xhrPost(xhrArgs);
	},
	/* 刷新分页信息 */
	refreshPager : function(data) {
		document.getElementById("page").innerHTML = data.page;
		document.getElementById("totalPage").innerHTML = data.totalPage;
	},
	/* 取得表格当前所选择的行对应的字段 */
	getSelectedRows : function(dataGridId, field) {
		var grid = dijit.byId(dataGridId);
		var items = grid.selection.getSelected();
		if (items.length) {
			var ids = new Array(items.length);
			var i = 0;
			dojo.forEach(items, function(selectedItem) {
				if (selectedItem !== null) {
					dojo.forEach(grid.store.getAttributes(selectedItem),
							function(attribute) {
								if (attribute == field) {
									var value = grid.store.getValues(
											selectedItem, field);
									ids[i++] = value;
								}
							});
				}
			});
			return ids;
		} else {
			return new Array(0);
		}
	},
	/* 弹出确认对话框 */
	confirm : function(message, confirmFun, confirmData) {
		if (!this.confirmDialog)
			this.confirmDialog = dijit.byId("confirm");

		if (message)
			dojo.byId("confirmMsg").innerHTML = message;

		if (confirmFun)
			this.confirmOK = confirmFun;

		this.confirmData = confirmData;

		this.confirmDialog.show();
	},
	/* 弹出提示信息 */
	showMessage : function(message, title) {
		if (!message) {
			return;
		}
		var dialog = this.confirmDialog = dijit.byId("message");
		if (title) {
			dialog.set("title", title);
		} else {
			dialog.set("title", "提示信息");
		}
		dojo.byId("messageTxt").innerHTML = message;
		dialog.show();
	},
	/* 确认YES */
	confirmYes : function() {
		this.confirmDialog.hide();
		this.confirmOK(this.confirmData);
	},
	/* 隐藏确认Dialog */
	confirmNo : function() {
		if (!this.confirmDialog) {
			this.confirmDialog = dijit.byId("confirm");
		}
		this.confirmDialog.hide();
		this.confirmOK = null;
		this.confirmData = null;
	},
	/* 查询数据 */
	search : function() {
		var url = dojo.attr("searchForm", "action");
		var params = dojo.formToObject("searchForm");
		Common.refreshDataGrid("jsonGrid", url, params);
	},
	/* 更新数据 */
	update : function(title, url) {
		var id = this.getSelectedRows("jsonGrid", "id");
		if (!id || id.length < 1) {
			this.showMessage("请选择一个需要修改的数据");
		} else if (id.length && id.length > 1) {
			this.showMessage("只能选择一条数据进行修改");
		} else {
			var tenant = Common.getSelectedRows("jsonGrid", "tenant");
			var id = Common.getSelectedRows("jsonGrid", "id");
			this.showDialog(title, url + '?entity.id=' + id + '&entity.tenant='
					+ tenant);
		}
	},
	/* 删除数据 */
	remove : function(url) {
		var ids = this.getSelectedRows("jsonGrid", "id");
		if (!ids || ids.length < 1) {
			this.showMessage("至少需要选择一条数据");
		} else {
			this.confirm("确认要删除当前选择的数据吗?", function(data) {
				Common.post(data.url, {
					selectedIds : data.ids
				}, function(response) {
					if (response.correct) {
						Common.search();
					}
				});
			}, {
				ids : ids.toString(),
				url : url
			});
		}
	},
	/* 关闭对话框，刷新默认表格 */
	closeDiaogAndRefresh : function(data) {
		if (!Common.hasErrors(data)) {
			Common.clearForm();
			Common.hideDialog();
			Common.search();
		}
	}
};