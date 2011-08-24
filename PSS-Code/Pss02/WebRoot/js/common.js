var Common = {
	dialog : null,
	dialogPane : null,
	confirmDialog : null,
	confirmOK : null,
	confirmData : null,
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
	/* 异步提交表单 */
	submitFormAsync : function(formId, success) {
		if (this.validateForm(formId)) {
			var xhrArgs = {
				form : dojo.byId(formId),
				handleAs : "json",
				load : function(data) {
					var formErrors = dojo.byId(formId + "Errors");
					if (formErrors != null) {
						dojo.empty(formErrors);
						if (data.errors != null) {
							for ( var field in data.errors) {
								dojo.create("li", {
									"innerHTML" : data.errors[field]
								}, formErrors);
							}
						}
					}
					success(data);
				}
			};
			dojo.xhrPost(xhrArgs);
		}
	},
	/* 同步提交表单 */
	submitForm : function(formId) {
		if (this.validateForm(formId)) {
			dojo.byId(formId).submit();
		}
	},
	// Post data
	post : function(url, data, success) {
		var xhrArgs = {
			url : url,
			handleAs : "json",
			content : data,
			load : function(response) {
				if (success)
					success(response);
			}
		};
		dojo.xhrPost(xhrArgs);
	},
	/* 清空表单 */
	clearForm : function(formId) {
		var form = dojo.byId(formId);
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
	/* 在页面上显示错误信息 */
	showErrors : function(data) {
		if (data == null || data == undefined) {
			return;
		}
		if (data.errorMessages != null && data.errorMessages.length > 0) {
			for ( var i = 0; i < data.errorMessages.length; i++) {

			}
		}
	},
	/* 刷新表格数据 */
	refreshDataGrid : function(tableId, url, data) {
		var grid = dijit.byId(tableId);
		if (!grid) {
			return;
		}
		if (!url) {
			url = grid.params;
		}
		var xhrArgs = {
			common : this,
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
				this.common.refreshPager(response);
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
	/* 定义一个什么都不做的空方法 */
	nullFunction : function() {

	},
	/* 在一个div下面添加一条错误信息 */
	addError : function() {

	}
};